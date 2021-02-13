package com.company.pizza.web.events;

import com.company.pizza.entity.Pizza;
import com.haulmont.cuba.gui.events.UiEvent;
import org.springframework.context.ApplicationEvent;

public class RemovePizzaEvent extends ApplicationEvent implements UiEvent {


    private final Pizza pizza;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public RemovePizzaEvent(Object source, Pizza pizza) {
        super(source);
        this.pizza = pizza;
    }

    public Pizza getPizza() {
        return pizza;
    }
}