package com.company.pizza.web.screens.pizzarecipe;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.PizzaRecipe;

@UiController("pizza_PizzaRecipe.browse")
@UiDescriptor("pizza-recipe-browse.xml")
@LookupComponent("pizzaRecipesTable")
@LoadDataBeforeShow
public class PizzaRecipeBrowse extends StandardLookup<PizzaRecipe> {
}