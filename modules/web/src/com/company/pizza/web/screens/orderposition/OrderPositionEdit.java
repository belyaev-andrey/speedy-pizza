package com.company.pizza.web.screens.orderposition;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.OrderPosition;

@UiController("pizza_OrderPosition.edit")
@UiDescriptor("order-position-edit.xml")
@EditedEntityContainer("orderPositionDc")
@LoadDataBeforeShow
public class OrderPositionEdit extends StandardEditor<OrderPosition> {
}