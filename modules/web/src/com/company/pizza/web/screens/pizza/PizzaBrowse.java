package com.company.pizza.web.screens.pizza;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Pizza;

@UiController("pizza_Pizza.browse")
@UiDescriptor("pizza-browse.xml")
@LookupComponent("pizzasTable")
@LoadDataBeforeShow
public class PizzaBrowse extends StandardLookup<Pizza> {
}