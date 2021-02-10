package com.company.pizza.web.screens.order;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Order;

@UiController("pizza_Order.browse")
@UiDescriptor("order-browse.xml")
@LookupComponent("ordersTable")
@LoadDataBeforeShow
public class OrderBrowse extends StandardLookup<Order> {
}