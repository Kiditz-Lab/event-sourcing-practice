package com.stafsus.practice.product.command

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.math.BigDecimal

data class RegisterProductCommand(
    @TargetAggregateIdentifier
    val productId: String,
    val title: String,
    val price: BigDecimal,
    val quantity: Int
)