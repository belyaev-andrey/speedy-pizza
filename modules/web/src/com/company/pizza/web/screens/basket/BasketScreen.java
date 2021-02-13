package com.company.pizza.web.screens.basket;

import com.company.pizza.data.Basket;
import com.company.pizza.entity.Order;
import com.company.pizza.service.OrderService;
import com.company.pizza.web.events.ClearBasketEvent;
import com.company.pizza.web.events.RemovePizzaEvent;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.KeyValueCollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@UiController("pizza_BasketScreen")
@UiDescriptor("basket-screen.xml")
@LoadDataBeforeShow
public class BasketScreen extends Screen {

    public static final String PIZZA = "pizza";
    public static final String COUNT = "count";
    public static final String MINUS = "minus";

    private Basket basket;

    @Inject
    private KeyValueCollectionLoader basketDl;

    @Inject
    private ScreenBuilders screenBuilders;

    @Inject
    private OrderService orderService;

    @Inject
    private Events events;

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Inject
    private DataGrid<KeyValueEntity> basketGrid;

    @Subscribe
    public void onInit(InitEvent event) {
        DataGrid.ClickableTextRenderer<KeyValueEntity> clickableTextRenderer =
                 basketGrid.createRenderer(DataGrid.ClickableTextRenderer.class);
        clickableTextRenderer.setRendererClickListener(clickEvent -> {
            KeyValueEntity item = clickEvent.getItem();
            events.publish(new RemovePizzaEvent(this, item.getValue(PIZZA)));
            basketDl.load();
        });
        basketGrid.getColumnNN("minus").setRenderer(clickableTextRenderer);
    }

    @Install(to = "basketDl", target = Target.DATA_LOADER)
    private List<KeyValueEntity> basketDlLoadDelegate(ValueLoadContext valueLoadContext) {
        List<KeyValueEntity> data = basket.getEntries().stream().map(e -> {
            KeyValueEntity entity = new KeyValueEntity();
            entity.setValue(PIZZA, e.getKey());
            entity.setValue(COUNT, e.getValue());
            entity.setValue(MINUS, "\uD83D\uDDD1");
            return entity;
        }).collect(Collectors.toList());

        return data;
    }

    @Subscribe("basketGrid.makeOrder")
    public void onBasketGridMakeOrder(Action.ActionPerformedEvent event) {
        final Order order = orderService.prepareOrderFromBasket(basket);
        screenBuilders.editor(Order.class, this)
                .editEntity(order)
                .withOpenMode(OpenMode.DIALOG).show();
        events.publish(new ClearBasketEvent(this));
        closeWithDefaultAction();
    }

    @Install(to = "basketGrid.makeOrder", subject = "enabledRule")
    private boolean basketGridMakeOrderEnabledRule() {
        return !basket.isEmpty();
    }

    @Subscribe("basketGrid.clear")
    public void onBasketGridClear(Action.ActionPerformedEvent event) {
        events.publish(new ClearBasketEvent(this));
        closeWithDefaultAction();
    }

    @Install(to = "basketGrid.clear", subject = "enabledRule")
    private boolean basketGridClearEnabledRule() {
        return !basket.isEmpty();
    }

    @Subscribe("basketGrid.continueShopping")
    public void onBasketGridContinueShopping(Action.ActionPerformedEvent event) {
        closeWithDefaultAction();
    }




}