package com.company.pizza.web.events;

import com.company.pizza.entity.Pizza;
import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class ClearBasketEvent extends ApplicationEvent implements UiEvent {

    public ClearBasketEvent(Object source) {
        super(source);
    }
}