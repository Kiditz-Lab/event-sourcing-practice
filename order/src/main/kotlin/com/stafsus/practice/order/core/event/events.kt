package com.stafsus.practice.order.core.event

import com.stafsus.practice.order.command.OrderStatus
import com.stafsus.practice.order.core.model.OrderStatus

data class OrderCreatedEvent(
    val orderId: String,
    val productId: String,
    val userId: String,
    val quantity: Int,
    val addressId: String,
    val status: OrderStatus
)