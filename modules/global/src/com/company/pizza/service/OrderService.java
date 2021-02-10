package com.company.pizza.service;

import com.company.pizza.entity.Order;

import java.math.BigDecimal;

public interface OrderService {
    String NAME = "pizza_OrderService";

    BigDecimal calculateCost(Order order);

    String generateNumber();

}