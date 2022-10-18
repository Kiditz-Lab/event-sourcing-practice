package com.stafsus.practice.order.core.event

import com.stafsus.practice.order.core.model.OrderStatus

class OrderCreatedEvent(
    val orderId: String,
    val productId: String,
    val userId: String,
    val quantity: Int,
    val addressId: String,
    val status: OrderStatus = OrderStatus.CREATED
)

data class OrderRejectedEvent(
    val orderId: String,
    val reason: String,
    val status: OrderStatus = OrderStatus.REJECTED
)