package com.company.pizza.web.screens.basket;

import com.company.pizza.web.screens.main.Basket;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.ValueLoadContext;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.ContentMode;
import com.haulmont.cuba.gui.components.DataGrid;
import com.haulmont.cuba.gui.model.KeyValueCollectionContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("pizza_BasketScreen")
@UiDescriptor("basket-screen.xml")
@LoadDataBeforeShow
public class BasketScreen extends Screen {

    private Basket basket;
    @Inject
    private KeyValueCollectionContainer basketDc;
    @Inject
    private Notifications notifications;

    public void setBasket(Basket basket) {
        this.basket = basket;
    }

    @Inject
    private DataGrid<KeyValueEntity> basketGrid;

    @Subscribe
    public void onInit(InitEvent event) {
        DataGrid.ClickableTextRenderer<KeyValueEntity> clickableTextRenderer =
                 basketGrid.createRenderer(DataGrid.ClickableTextRenderer.class);
        clickableTextRenderer.setRendererClickListener(clickEvent -> {
            KeyValueEntity item = clickEvent.getItem();
            notifications.create()
                    .withContentMode(ContentMode.HTML)
                    .withCaption("<b>" + item.getValue("pizza") + "</b>")
                    .show();
        });
        basketGrid.getColumnNN("minus").setRenderer(clickableTextRenderer);
    }



    @Install(to = "basketDl", target = Target.DATA_LOADER)
    private List<KeyValueEntity> basketDlLoadDelegate(ValueLoadContext valueLoadContext) {
        List<KeyValueEntity> data = basket.getEntries().stream().map(e -> {
            KeyValueEntity entity = new KeyValueEntity();
            entity.setValue("pizza", e.getKey());
            entity.setValue("count", e.getValue());
            entity.setValue("minus", "\uD83D\uDDD1");
            return entity;
        }).collect(Collectors.toList());
        return data;
    }



}