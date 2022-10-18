package com.stafsus.practice.order.commands

import com.stafsus.practice.order.core.model.OrderStatus
import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateOrderCommand(
    @TargetAggregateIdentifier
    val orderId: String = UUID.randomUUID().toString(),
    val productId: String,
    val userId: String = "27b95829-4f3f-4ddf-8983-151ba010e35b",
    val quantity: Int,
    val addressId: String,
    val status: OrderStatus = OrderStatus.CREATED
)


