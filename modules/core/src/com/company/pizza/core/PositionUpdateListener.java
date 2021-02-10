package com.company.pizza.core;

import com.company.pizza.entity.OrderPosition;
import com.company.pizza.service.OrderService;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.listener.BeforeUpdateEntityListener;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component(PositionUpdateListener.NAME)
public class PositionUpdateListener implements BeforeUpdateEntityListener<OrderPosition> {
    public static final String NAME = "pizza_PositionUpdateListener";

    @Inject
    private OrderService orderService;

    @Override
    public void onBeforeUpdate(OrderPosition entity, EntityManager entityManager) {
        entity.getOrder().setTotalAmount(orderService.calculateCost(entity.getOrder()));
    }
}