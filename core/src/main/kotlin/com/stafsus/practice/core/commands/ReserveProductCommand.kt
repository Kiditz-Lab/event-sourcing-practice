package com.stafsus.practice.core.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ReserveProductCommand(
    @TargetAggregateIdentifier
    val productId: String,
    val orderId: String,
    val userId: String,
    val quantity: Int,
)