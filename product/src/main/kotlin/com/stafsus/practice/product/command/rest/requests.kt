package com.stafsus.practice.product.command.rest

import java.math.BigDecimal

data class RegisterProductRequest(
    val title: String,
    val price: BigDecimal,
    val quantity: Int
)