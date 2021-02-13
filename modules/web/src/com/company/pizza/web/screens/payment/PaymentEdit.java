package com.company.pizza.web.screens.payment;

import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Payment;

@UiController("pizza_Payment.edit")
@UiDescriptor("payment-edit.xml")
@EditedEntityContainer("paymentDc")
@LoadDataBeforeShow
public class PaymentEdit extends StandardEditor<Payment> {
}