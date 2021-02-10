package com.company.pizza.web.screens.main;

import com.company.pizza.web.events.PizzaInBasketEvent;
import com.company.pizza.web.screens.basket.BasketScreen;
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

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        refreshBasketLabel();
    }

    @EventListener
    public void pizzaAdded(PizzaInBasketEvent event) {
        basket.addToBasket(event.getPizza());
        refreshBasketLabel();
    }

    private void refreshBasketLabel() {
        basketLabel.setCaption(messageBundle.getMessage("basket.label")+"("+ basket.getItemsCount() +")");
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