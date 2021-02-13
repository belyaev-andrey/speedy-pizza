package com.company.pizza.service;

import com.company.pizza.data.Basket;
import com.company.pizza.entity.Delivery;
import com.company.pizza.entity.Order;
import com.company.pizza.entity.OrderPosition;
import com.company.pizza.entity.Pizza;
import com.haulmont.cuba.core.app.UniqueNumbersAPI;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.*;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service(OrderService.NAME)
public class OrderServiceBean implements OrderService {

    @Inject
    private UniqueNumbersAPI uniqueNumbersAPI;
    @Inject
    private DataManager dataManager;
    @Inject
    private EntityStates entityStates;

    @Override
    public Order prepareOrderFromBasket(Basket basket) {
        final Order order = dataManager.create(Order.class);
        order.setOrderNo(generateNumber());
        List<OrderPosition> positions = basket.getEntries().stream().map(e -> {
            OrderPosition position = dataManager.create(OrderPosition.class);
            position.setOrder(order);
            position.setPizza(e.getKey());
            position.setAmount(e.getValue());
            return position;
        }).collect(Collectors.toList());
        order.setPositions(positions);
        Collection<Entity<UUID>> entitiesToSave = new ArrayList<>(positions.size()+1);
        entitiesToSave.add(order);
        entitiesToSave.addAll(positions);

        return dataManager.commit(entitiesToSave.toArray(new Entity[]{})).get(order);
    }

    @Override
    public BigDecimal calculateCost(Order order) {
        BigDecimal deliveryCost = BigDecimal.valueOf(100);
        BigDecimal initialPrice = calculateInitialPrice(order.getPositions());
        BigDecimal discount = BigDecimal.ONE.subtract(BigDecimal.valueOf(calculateDiscount(order)).divide(BigDecimal.valueOf(100)));
        if (initialPrice.compareTo(BigDecimal.valueOf(1500)) > 0) {
            deliveryCost = BigDecimal.ZERO;
        }
        return initialPrice.multiply(discount).add(deliveryCost);
    }

    private int calculateDiscount(Order order) {
        int discount = 0;
        if (order.getDelivery() != null && order.getDelivery().getEmail() != null) {
            discount = 10;
            if (getOrdersCount(order.getDelivery().getEmail()) > 100) {
                discount = 20;
            }
        } else {
            int pizzas = countPizzas(order.getPositions());
            if (pizzas > 3 && pizzas < 8) {
                discount = 5;
            } if (pizzas > 8) {
                discount = 10;
            }
        }
        return discount;
    }

    private long getOrdersCount(String email) {
        return dataManager.getCount(new LoadContext<>(Delivery.class).setQuery(
                new LoadContext.Query("select d from pizza_Delivery d where d.email = :email").setParameter("email", email)
        ));
    }

    private int countPizzas(List<OrderPosition> positionList) {
        Set<Pizza> pizzaSet = new HashSet<>();
        positionList.forEach(p -> pizzaSet.add(p.getPizza()));
        return pizzaSet.size();
    }

    private BigDecimal calculateInitialPrice(List<OrderPosition> positionList) {
        BigDecimal cost = BigDecimal.ZERO;
        if (positionList != null) {
            for (OrderPosition p : positionList) {
                Pizza pizza = p.getPizza();
                if (pizza != null) {
                    if (!entityStates.isLoaded(pizza, "price")) {
                        pizza = dataManager.reload(pizza, View.LOCAL);
                    }
                    cost = cost.add(pizza.getPrice().multiply(BigDecimal.valueOf(p.getAmount())));
                }
            }
        }
        return cost;
    }

    @Override
    public String generateNumber() {
        LocalDateTime now = LocalDateTime.now();
        String s = now.getDayOfWeek().name().substring(0, 1);
        long number = uniqueNumbersAPI.getNextNumber("ORDER");
        return String.format("%s-%d-%d", s, number, getShiftNumber(now));
    }

    private int getShiftNumber(LocalDateTime dateTime) {
        int hour = dateTime.getHour();
        if (hour >= 8 && hour < 17) {
            return 1;
        } else if (hour >= 17 && hour < 22) {
            return 2;
        }
        return 3;
    }

}