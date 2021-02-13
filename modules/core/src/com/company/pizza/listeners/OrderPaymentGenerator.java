package com.company.pizza.listeners;

import com.company.pizza.entity.Order;

import java.math.BigDecimal;
import java.util.UUID;

import com.company.pizza.entity.OrderStatus;
import com.company.pizza.entity.Payment;
import com.haulmont.cuba.core.app.events.EntityChangedEvent;
import com.haulmont.cuba.core.entity.contracts.Id;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.inject.Inject;

@Component("pizza_OrderPaymentGenerator")
public class OrderPaymentGenerator {

    @Inject
    private DataManager dataManager;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void afterCommit(EntityChangedEvent<Order, UUID> event) {
        if (!event.getChanges().isChanged("status")) {
            return;
        }

        final Order order = dataManager.load(event.getEntityId())
                .view(View.LOCAL)
                .one();

        if (order.getStatus() == OrderStatus.PAID) {
            final Payment payment = dataManager.create(Payment.class);
            payment.setOrder(order);
            payment.setAmount(order.getTotalAmount());
            dataManager.commit(payment);
        }

        if (order.getStatus() == OrderStatus.CANCELED) {
            final Payment payment = dataManager.create(Payment.class);
            payment.setOrder(order);
            OrderStatus oldStatus = OrderStatus.fromId(event.getChanges().getOldValue("status"));
            if (oldStatus == OrderStatus.COOKING) {
                payment.setAmount(order.getTotalAmount().multiply(BigDecimal.valueOf(-1)));
            }
            dataManager.commit(payment);
        }

    }
}