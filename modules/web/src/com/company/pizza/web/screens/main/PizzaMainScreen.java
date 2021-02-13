package com.company.pizza.web.screens.main;

import com.company.pizza.data.Basket;
import com.company.pizza.entity.Pizza;
import com.company.pizza.web.events.AddPizzaEvent;
import com.company.pizza.web.events.ClearBasketEvent;
import com.company.pizza.web.events.RemovePizzaEvent;
import com.company.pizza.web.screens.basket.BasketScreen;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.LinkButton;
import com.haulmont.cuba.gui.screen.*;
import com.haulmont.cuba.web.app.main.MainScreen;
import org.springframework.context.event.EventListener;

import javax.inject.Inject;


@UiController("pizzaMainScreen")
@UiDescriptor("pizza-main-screen.xml")
public class PizzaMainScreen extends MainScreen {

    private final Basket basket = new Basket();

    @Inject
    private LinkButton basketLabel;

    @Inject
    private MessageBundle messageBundle;
    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private Action openBasket;
    @Inject
    private Notifications notifications;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        refreshBasketLabel();
    }

    @EventListener
    public void pizzaAdded(AddPizzaEvent event) {
        final Pizza pizza = event.getPizza();
        basket.addToBasket(pizza);
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(String.format(messageBundle.getMessage("pizza.name.added"), pizza.getName()))
                .show();
        refreshBasketLabel();
    }

    @EventListener
    public void pizzaRemoved(RemovePizzaEvent event) {
        final Pizza pizza = event.getPizza();
        basket.removeFromBasket(pizza);
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(String.format(messageBundle.getMessage("pizza.name.removed"), pizza.getName()))
                .show();
        refreshBasketLabel();
    }

    @EventListener
    public void pizzaRemovedAll(ClearBasketEvent event) {
        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(messageBundle.getMessage("basket.cleared"))
                .show();
        basket.clear();
        refreshBasketLabel();
    }


    private void refreshBasketLabel() {
        basketLabel.setCaption(String.format(messageBundle.getMessage("basket.label"), basket.getItemsCount()));
    }

    @Subscribe("openBasket")
    public void onOpenBasket(Action.ActionPerformedEvent event) {
        BasketScreen basketScreen = screenBuilders.
                screen(this)
                .withScreenClass(BasketScreen.class)
                .withOpenMode(OpenMode.DIALOG)
                .build();
        basketScreen.setBasket(basket);
        basketScreen.show();
    }

    @Subscribe("basketLabel")
    public void onBasketLabelClick(Button.ClickEvent event) {
        openBasket.actionPerform(event.getButton());
    }

}