package com.company.pizza.web.screens.ingredient;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Ingredient;

@UiController("pizza_Ingredient.edit")
@UiDescriptor("ingredient-edit.xml")
@EditedEntityContainer("ingredientDc")
@LoadDataBeforeShow
public class IngredientEdit extends StandardEditor<Ingredient> {
}