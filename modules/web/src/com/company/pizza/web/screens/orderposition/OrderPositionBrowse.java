package com.company.pizza.web.screens.orderposition;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.OrderPosition;

@UiController("pizza_OrderPosition.browse")
@UiDescriptor("order-position-browse.xml")
@LookupComponent("orderPositionsTable")
@LoadDataBeforeShow
public class OrderPositionBrowse extends StandardLookup<OrderPosition> {
}