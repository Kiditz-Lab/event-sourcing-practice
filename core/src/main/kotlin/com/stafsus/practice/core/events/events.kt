package com.stafsus.practice.core.events

data class ProductReservedEvent(
    val productId: String,
    val orderId: String,
    val userId: String,
    val quantity: Int,
)

data class PaymentProcessedEvent(
    val paymentId: String,
    val orderId: String,
)

data class ProductReservationCancelledEvent(
    val productId: String,
    val orderId: String,
    val userId: String,
    val quantity: Int,
    val reason: String,
)