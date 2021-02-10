package com.company.pizza.web.screens.order;

import com.company.pizza.entity.Delivery;
import com.company.pizza.entity.OrderPosition;
import com.company.pizza.entity.OrderStatus;
import com.company.pizza.service.OrderService;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
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

    @Subscribe
    public void onInitEntity(InitEntityEvent<Order> event) {
        final Delivery delivery = dataContext.create(Delivery.class);
        final Order order = event.getEntity();
        order.setOrderNo(orderService.generateNumber());
        order.setStatus(OrderStatus.CREATED);
        order.setCreated(LocalDateTime.now());
        delivery.setOrder(order);
        order.setDelivery(delivery);
    }

    @Subscribe("positionsGrid.addNewPosition")
    public void onPositionsGridAddNewPosition(Action.ActionPerformedEvent event) {
        final OrderPosition orderPosition = dataContext.create(OrderPosition.class);
        final Order order = getEditedEntity();
        orderPosition.setOrder(order);
        orderPosition.setAmount(1);
        positionsDc.replaceItem(orderPosition);
        orderDc.setItem(order);
    }

    @Subscribe("positionsGrid.removePosition")
    public void onPositionsGridRemovePosition(Action.ActionPerformedEvent event) {
        positionsGrid.getSelected().forEach(pos -> {
            dataContext.remove(pos);
            positionsDc.getMutableItems().remove(pos);
        });
    }

    @Install(to = "positionsGrid.removePosition", subject = "enabledRule")
    private boolean positionsGridRemovePositionEnabledRule() {
        return !positionsGrid.getSelected().isEmpty();
    }



}