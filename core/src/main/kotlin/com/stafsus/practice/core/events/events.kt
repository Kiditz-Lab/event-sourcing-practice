package com.stafsus.practice.core.events

data class ProductReservedEvent(
    val productId: String,
    val orderId: String,
    val userId: String,
    val quantity: Int,
)