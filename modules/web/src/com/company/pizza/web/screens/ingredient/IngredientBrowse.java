package com.company.pizza.web.screens.ingredient;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Ingredient;

@UiController("pizza_Ingredient.browse")
@UiDescriptor("ingredient-browse.xml")
@LookupComponent("ingredientsTable")
@LoadDataBeforeShow
public class IngredientBrowse extends StandardLookup<Ingredient> {
}