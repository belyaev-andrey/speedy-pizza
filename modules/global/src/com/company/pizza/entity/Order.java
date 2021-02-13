package com.company.pizza.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.PublishEntityChangedEvents;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@PublishEntityChangedEvents
@Table(name = "PIZZA_ORDER")
@Entity(name = "pizza_Order")
@NamePattern("%s %s|orderNo,status")
@Listeners("pizza_OrderSaveListener")
public class Order extends StandardEntity {
    private static final long serialVersionUID = 7434331762778569833L;

    @NotNull
    @Column(name = "ORDER_NO", nullable = false, unique = true)
    private String orderNo;

    @NotNull
    @Column(name = "CREATED", nullable = false)
    private LocalDateTime created;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    private Integer status;

    @OneToMany(mappedBy = "order")
    private List<OrderPosition> positions;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    private Delivery delivery;

    @Column(name = "TOTAL_AMOUNT")
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public List<OrderPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderPosition> positions) {
        this.positions = positions;
    }

    public OrderStatus getStatus() {
        return status == null ? null : OrderStatus.fromId(status);
    }

    public void setStatus(OrderStatus status) {
        this.status = status == null ? null : status.getId();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @PostConstruct
    public void postConstruct() {
        status = OrderStatus.CREATED.getId();
        created = LocalDateTime.now();
        totalAmount = BigDecimal.ZERO;
    }
}