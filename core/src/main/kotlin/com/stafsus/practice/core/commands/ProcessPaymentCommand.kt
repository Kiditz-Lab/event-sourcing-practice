package com.stafsus.practice.core.commands

import com.stafsus.practice.core.model.PaymentDetails
import org.axonframework.modelling.command.TargetAggregateIdentifier

data class ProcessPaymentCommand(
    @TargetAggregateIdentifier
    val paymentId: String,
    val orderId: String,
    val paymentDetails: PaymentDetails
)