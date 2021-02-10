package com.company.pizza.core;

import com.company.pizza.entity.Order;
import com.company.pizza.service.OrderService;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(OrderSaveListener.NAME)
public class OrderSaveListener implements BeforeInsertEntityListener<Order>, BeforeUpdateEntityListener<Order> {
    public static final String NAME = "pizza_OrderSaveListener";

    @Inject
    private OrderService orderService;

    @Override
    public void onBeforeInsert(Order entity, EntityManager entityManager) {
        entity.setTotalAmount(orderService.calculateCost(entity));
    }

    @Override
    public void onBeforeUpdate(Order entity, EntityManager entityManager) {
        entity.setTotalAmount(orderService.calculateCost(entity));
    }
}