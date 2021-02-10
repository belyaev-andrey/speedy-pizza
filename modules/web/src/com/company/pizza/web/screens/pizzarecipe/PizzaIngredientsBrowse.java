package com.company.pizza.web.screens.pizzarecipe;

import com.company.pizza.entity.Pizza;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.PizzaRecipe;

import javax.inject.Inject;

@UiController("pizza_PizzaIngredients.browse")
@UiDescriptor("pizza-ingredients-browse.xml")
@LookupComponent("pizzaRecipesTable")
@LoadDataBeforeShow
public class PizzaIngredientsBrowse extends StandardLookup<PizzaRecipe> {

    @Inject
    private CollectionLoader<PizzaRecipe> pizzaRecipesDl;

    public void setFilter(Pizza pizza) {
        pizzaRecipesDl.setParameter("pizza", pizza);
    }

}