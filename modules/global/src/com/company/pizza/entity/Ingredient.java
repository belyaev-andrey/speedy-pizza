package com.company.pizza.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name = "PIZZA_INGREDIENT")
@Entity(name = "pizza_Ingredient")
@NamePattern("%s|name")
public class Ingredient extends StandardEntity {
    private static final long serialVersionUID = 4777002513880895452L;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ALLERGIC")
    private Boolean allergic;

    public Boolean getAllergic() {
        return allergic;
    }

    public void setAllergic(Boolean allergic) {
        this.allergic = allergic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}