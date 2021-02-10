package com.company.pizza.web.screens.pizzarecipe;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.PizzaRecipe;

@UiController("pizza_PizzaRecipe.edit")
@UiDescriptor("pizza-recipe-edit.xml")
@EditedEntityContainer("pizzaRecipeDc")
@LoadDataBeforeShow
public class PizzaRecipeEdit extends StandardEditor<PizzaRecipe> {
}