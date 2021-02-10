package com.company.pizza.web.screens.main;

import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.HasContextHelp;
import com.haulmont.cuba.gui.components.Image;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.web.app.main.MainScreen;

import javax.inject.Inject;


@UiController("pizzaMainScreen")
@UiDescriptor("pizza-main-screen.xml")
public class PizzaMainScreen extends MainScreen {
    @Inject
    private Notifications notifications;

    @Subscribe("basketLabel")
    public void onBasketLabelClick(Button.ClickEvent event) {
        notifications.create().withCaption("Link").show();
    }

}