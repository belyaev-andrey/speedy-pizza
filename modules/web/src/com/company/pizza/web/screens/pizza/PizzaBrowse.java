package com.company.pizza.web.screens.pizza;

import com.company.pizza.web.events.PizzaInBasketEvent;
import com.company.pizza.web.screens.pizzarecipe.PizzaIngredientsBrowse;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Pizza;
import com.haulmont.cuba.security.global.UserSession;

import javax.inject.Inject;

@UiController("pizza_Pizza.browse")
@UiDescriptor("pizza-browse.xml")
@LookupComponent("pizzasTable")
@LoadDataBeforeShow
public class PizzaBrowse extends StandardLookup<Pizza> {

    @Inject
    private ScreenBuilders screenBuilders;
    @Inject
    private GroupTable<Pizza> pizzasTable;
    @Inject
    private UserSession userSession;
    @Inject
    private Events events;

    @Subscribe("pizzasTable.showRecipe")
    public void onPizzasTableShowRecipe(Action.ActionPerformedEvent event) {
        final PizzaIngredientsBrowse browse = screenBuilders.screen(this)
                .withScreenClass(PizzaIngredientsBrowse.class)
                .withOpenMode(OpenMode.DIALOG)
                .build();
        browse.setFilter(pizzasTable.getSingleSelected());
        browse.show();
    }

    @Install(to = "pizzasTable.showRecipe", subject = "enabledRule")
    private boolean pizzasTableShowRecipeEnabledRule() {
        return !pizzasTable.getSelected().isEmpty();
    }

    @Subscribe("pizzasTable.orderPizza")
    public void onPizzasTableOrderPizza(Action.ActionPerformedEvent event) {
        pizzasTable.getSelected().forEach(p -> {
            events.publish(new PizzaInBasketEvent(this, p));
        });
    }

    @Install(to = "pizzasTable.orderPizza", subject = "enabledRule")
    private boolean pizzasTableOrderPizzaEnabledRule() {
        return !pizzasTable.getSelected().isEmpty();
    }



}