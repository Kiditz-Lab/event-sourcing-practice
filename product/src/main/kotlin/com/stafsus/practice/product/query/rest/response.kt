package com.stafsus.practice.product.query.rest

import java.math.BigDecimal

data class ProductResponse(
    val productId: String,
    val title: String,
    val price: BigDecimal,
    val quantity: Int
)