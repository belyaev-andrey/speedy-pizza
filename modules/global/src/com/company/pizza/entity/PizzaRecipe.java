package com.company.pizza.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Table(name = "PIZZA_PIZZA_RECIPE")
@Entity(name = "pizza_PizzaRecipe")
public class PizzaRecipe extends StandardEntity {
    private static final long serialVersionUID = 3361969544327493978L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "PIZZA_ID")
    @NotNull
    private Pizza pizza;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INGREDIENT_ID")
    private Ingredient ingredient;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    @PositiveOrZero
    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    @PostConstruct
    public void postConstruct() {
        amount = 0;
    }
}