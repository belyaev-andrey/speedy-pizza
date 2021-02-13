package com.company.pizza.web.screens.payment;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Payment;

@UiController("pizza_Payment.browse")
@UiDescriptor("payment-browse.xml")
@LookupComponent("paymentsTable")
@LoadDataBeforeShow
public class PaymentBrowse extends StandardLookup<Payment> {
}