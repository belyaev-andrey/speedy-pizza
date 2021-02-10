package com.company.pizza.web.screens.pizza;

import com.company.pizza.entity.PizzaRecipe;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.CollectionPropertyContainer;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Pizza;

import javax.inject.Inject;
import java.util.ArrayList;

@UiController("pizza_Pizza.edit")
@UiDescriptor("pizza-edit.xml")
@EditedEntityContainer("pizzaDc")
@LoadDataBeforeShow
public class PizzaEdit extends StandardEditor<Pizza> {

    @Inject
    private DataContext dataContext;

    @Inject
    private CollectionPropertyContainer<PizzaRecipe> ingredientsDc;

    @Inject
    private DataGrid<PizzaRecipe> ingredientsGrid;

    @Subscribe("ingredientsGrid.addIngredient")
    public void onIngredientsGridAddIngredient(Action.ActionPerformedEvent event) {
        PizzaRecipe pizzaRecipe = dataContext.create(PizzaRecipe.class);
        Pizza pizza = getEditedEntity();
        pizzaRecipe.setPizza(pizza);
        ingredientsDc.replaceItem(pizzaRecipe);
    }

    @Install(to = "ingredientsGrid.removeIngredient", subject = "enabledRule")
    private boolean ingredientsGridRemoveIngredientEnabledRule() {
        return !ingredientsGrid.getSelected().isEmpty();
    }

    @Subscribe("ingredientsGrid.removeIngredient")
    public void onIngredientsGridRemoveIngredient(Action.ActionPerformedEvent event) {
        ingredientsGrid.getSelected().forEach(ingr -> {
            dataContext.remove(ingr);
            ingredientsDc.getMutableItems().remove(ingr);
        });
    }


}