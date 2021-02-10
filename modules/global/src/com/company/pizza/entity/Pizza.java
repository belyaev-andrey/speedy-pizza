package com.company.pizza.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.List;

@Table(name = "PIZZA_PIZZA")
@Entity(name = "pizza_Pizza")
@NamePattern("%s (%s)|name,diameter")
public class Pizza extends StandardEntity {
    private static final long serialVersionUID = 4186425602332203836L;

    @NotNull
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @NotNull
    @Column(name = "DIAMETER", nullable = false)
    @Positive
    private Integer diameter;

    @Column(name = "CALORIES")
    @PositiveOrZero
    private Integer calories;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    @PositiveOrZero
    private BigDecimal price;

    @OneToMany(mappedBy = "pizza")
    private List<PizzaRecipe> ingredients;

    @JoinColumn(name = "IMAGE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private FileDescriptor image;

    public FileDescriptor getImage() {
        return image;
    }

    public void setImage(FileDescriptor image) {
        this.image = image;
    }

    public List<PizzaRecipe> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<PizzaRecipe> ingredients) {
        this.ingredients = ingredients;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}