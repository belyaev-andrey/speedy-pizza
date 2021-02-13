package com.company.pizza.web.screens.order;

import com.company.pizza.entity.Delivery;
import com.company.pizza.entity.OrderPosition;
import com.company.pizza.entity.OrderStatus;
import com.company.pizza.service.OrderService;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Order;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.ArrayList;

@UiController("pizza_Order.edit")
@UiDescriptor("order-edit.xml")
@EditedEntityContainer("orderDc")
@LoadDataBeforeShow
public class OrderEdit extends StandardEditor<Order> {

    @Inject
    private DataContext dataContext;
    @Inject
    private CollectionPropertyContainer<OrderPosition> positionsDc;

    @Inject
    private DataGrid<OrderPosition> positionsGrid;

    @Inject
    private OrderService orderService;

    @Inject
    private InstanceContainer<Order> orderDc;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Dialogs dialogs;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Order> event) {
        final Delivery delivery = dataContext.create(Delivery.class);
        final Order order = event.getEntity();
        order.setOrderNo(orderService.generateNumber());
        delivery.setOrder(order);
        order.setDelivery(delivery);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        final Order order = getEditedEntity();
        if (order.getDelivery() == null) {
            final Delivery delivery = dataContext.create(Delivery.class);
            order.setDelivery(delivery);
            delivery.setOrder(order);
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption(messageBundle.getMessage("delivery.created"))
                    .show();
        }
    }

    @Subscribe
    public void onBeforeClose(BeforeCloseEvent event) {
        final Order order = getEditedEntity();
        if (order.getPositions().isEmpty() && (order.getStatus() != OrderStatus.CANCELED) && event.closedWith(StandardOutcome.COMMIT)) {
            event.preventWindowClose();
            dialogs.createOptionDialog(Dialogs.MessageType.CONFIRMATION)
                    .withCaption(messageBundle.getMessage("confirmation"))
                    .withMessage(messageBundle.getMessage("confirm.order.cancel"))
                    .withActions(
                            new DialogAction(DialogAction.Type.YES, Action.Status.PRIMARY).withHandler(e -> {
                                order.setStatus(OrderStatus.CANCELED);
                                OrderEdit.this.closeWithCommit();
                            }),
                            new DialogAction(DialogAction.Type.NO)
                    ).show();
        }

    }



    @Subscribe("positionsGrid.addNewPosition")
    public void onPositionsGridAddNewPosition(Action.ActionPerformedEvent event) {
        final OrderPosition orderPosition = dataContext.create(OrderPosition.class);
        final Order order = getEditedEntity();
        orderPosition.setOrder(order);
        orderPosition.setAmount(1);
        positionsDc.replaceItem(orderPosition);
        orderDc.setItem(order);
        order.setTotalAmount(orderService.calculateCost(order));
    }

    @Subscribe("positionsGrid.removePosition")
    public void onPositionsGridRemovePosition(Action.ActionPerformedEvent event) {
        positionsGrid.getSelected().forEach(pos -> {
            dataContext.remove(pos);
            positionsDc.getMutableItems().remove(pos);
        });
        final Order order = getEditedEntity();
        order.setTotalAmount(orderService.calculateCost(order));
    }

    @Subscribe(id = "positionsDc", target = Target.DATA_CONTAINER)
    public void onPositionsDcItemPropertyChange(InstanceContainer.ItemPropertyChangeEvent<OrderPosition> event) {
        final Order order = getEditedEntity();
        order.setTotalAmount(orderService.calculateCost(order));
    }

    @Install(to = "positionsGrid.removePosition", subject = "enabledRule")
    private boolean positionsGridRemovePositionEnabledRule() {
        return !positionsGrid.getSelected().isEmpty();
    }



}