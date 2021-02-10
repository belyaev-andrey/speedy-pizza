package com.company.pizza.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Table(name = "PIZZA_DELIVERY")
@Entity(name = "pizza_Delivery")
@NamePattern("%s %s %s|street,name,phoneNum")
public class Delivery extends StandardEntity {
    private static final long serialVersionUID = 4615609907448565211L;

    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "PHONE_NUM", nullable = false)
    @NotNull
    private String phoneNum;

    @Column(name = "EMAIL")
    @Email
    private String email;

    @NotNull
    @Column(name = "CITY", nullable = false)
    private String city;

    @Column(name = "STREET")
    private String street;

    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;

    @Column(name = "APARTMENT_NUMBER")
    private String apartmentNumber;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERED_BY_ID")
    private User deliveredBy;

    @Column(name = "DELIVERED_AT")
    private LocalDateTime deliveredAt;

    @Lookup(type = LookupType.DROPDOWN, actions = "lookup")
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ID")
    @NotNull
    private Order order;

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(User deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}