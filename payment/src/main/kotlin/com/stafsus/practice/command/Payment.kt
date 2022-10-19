package com.stafsus.practice.command

import com.stafsus.practice.core.commands.ProcessPaymentCommand
import com.stafsus.practice.core.events.PaymentProcessedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.spring.stereotype.Aggregate
import org.axonframework.modelling.command.AggregateLifecycle.apply

@Aggregate
class Payment {
    @AggregateIdentifier
    private lateinit var paymentId: String
    private lateinit var orderId: String

    constructor()

    @CommandHandler
    constructor(command: ProcessPaymentCommand) {
        apply(PaymentProcessedEvent(command.paymentId, command.orderId))
    }

    @EventSourcingHandler
    fun on(event: ProcessPaymentCommand) {
        paymentId = event.paymentId
        orderId = event.orderId
    }
}