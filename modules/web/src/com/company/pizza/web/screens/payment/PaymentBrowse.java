package com.company.pizza.web.screens.payment;

import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.screen.*;
import com.company.pizza.entity.Payment;
import com.haulmont.cuba.web.gui.components.renderers.WebHtmlRenderer;
import com.haulmont.cuba.web.gui.components.renderers.WebTextRenderer;
import com.vaadin.ui.renderers.HtmlRenderer;

import javax.inject.Inject;
import java.math.BigDecimal;

@UiController("pizza_Payment.browse")
@UiDescriptor("payment-browse.xml")
@LookupComponent("paymentsTable")
@LoadDataBeforeShow
public class PaymentBrowse extends StandardLookup<Payment> {

    @Inject
    private DataGrid<Payment> paymentsTable;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        final DataGrid.Column<Payment> column = paymentsTable.getColumn("amount");
        final WebHtmlRenderer renderer = new WebHtmlRenderer();
        column.setRenderer(renderer, value -> {
            final BigDecimal bigDecimal = (BigDecimal) value;
            String style = "color:"+(BigDecimal.ZERO.compareTo(bigDecimal) > 0 ? "red":"green");
            return value == null? "" : "<span style='"+style+"'>"+ bigDecimal +"</span>";
        });
    }



}