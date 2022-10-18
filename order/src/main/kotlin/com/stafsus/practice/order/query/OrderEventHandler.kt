package com.stafsus.practice.order.query

import com.stafsus.practice.order.core.data.OrderEntity
import com.stafsus.practice.order.core.data.OrderRepository
import com.stafsus.practice.order.core.event.OrderCreatedEvent
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("order-group")
class OrderEventHandler(
    private val repo: OrderRepository
) {
    @EventHandler
    fun on(event: OrderCreatedEvent) {
        val order = OrderEntity(
            orderId = event.orderId,
            productId = event.productId,
            userid = event.userId,
            addressid = event.addressId,
            quantity = event.quantity,
            status = event.status
        )
        repo.save(order)
    }
}