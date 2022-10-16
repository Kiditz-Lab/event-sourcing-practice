package com.stafsus.practice.product.core.event

import java.math.BigDecimal

data class ProductRegisteredEvent(
    val productId: String,
    val title: String,
    val price: BigDecimal,
    val quantity: Int
)