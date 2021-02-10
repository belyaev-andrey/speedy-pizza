package com.company.pizza.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public enum OrderStatus implements EnumClass<Integer> {

    CREATED(10),
    PAID(20),
    COOKING(30),
    READY(40),
    IN_DELIVERY(50),
    DELIVERED(60),
    CANCELED(70);

    private final Integer id;

    OrderStatus(Integer value) {
        this.id = value;
    }

    @Nonnull
    public Integer getId() {
        return id;
    }

    @Nullable
    public static OrderStatus fromId(Integer id) {
        for (OrderStatus at : OrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}