package com.company.pizza.web.screens.main;

import com.company.pizza.entity.Pizza;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Basket implements Serializable {

    private final Map<Pizza, Integer> basket = new HashMap<>();

    public void addToBasket(Pizza pizza) {
        basket.computeIfPresent(pizza, (pizz, count) -> count+1);
        basket.putIfAbsent(pizza, 1);
    }

    public Integer getItemsCount() {
        return basket.values().stream().reduce(0, Integer::sum);
    }

    public void removeFromBasket(Pizza pizza) {
        basket.computeIfPresent(pizza, (pizz, count) -> count-1);
        if (basket.getOrDefault(pizza, 0) == 0) {
            basket.remove(pizza);
        }
    }

    public Set<Map.Entry<Pizza, Integer>> getEntries() {
        return basket.entrySet();
    }


}