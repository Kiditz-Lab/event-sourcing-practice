package com.stafsus.practice.product.command

import com.stafsus.practice.core.commands.CancelProductReservationCommand
import com.stafsus.practice.core.commands.ReserveProductCommand
import com.stafsus.practice.core.events.ProductReservationCancelledEvent
import com.stafsus.practice.core.events.ProductReservedEvent
import com.stafsus.practice.product.core.event.ProductRegisteredEvent
import org.apache.commons.lang.StringUtils.isBlank
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate
import java.math.BigDecimal

@Aggregate
class Product {
    @AggregateIdentifier
    private lateinit var productId: String
    private lateinit var title: String
    private lateinit var price: BigDecimal
    private var quantity: Int = 0

    constructor()

    @CommandHandler
    constructor(command: RegisterProductCommand) {
        if (command.price <= BigDecimal.ZERO) {
            throw IllegalArgumentException("Price cannot be less or equal zero")
        }
        if (isBlank(command.title)) {
            throw IllegalArgumentException("Title cannot be empty")
        }
        apply(ProductRegisteredEvent(command.productId, command.title, command.price, command.quantity))
    }

    @EventSourcingHandler
    fun on(event: ProductRegisteredEvent) {
        productId = event.productId
        title = event.title
        price = event.price
        quantity = event.quantity
    }

    @CommandHandler
    fun handle(command: ReserveProductCommand) {
        if (quantity < command.quantity) {
            throw IllegalArgumentException("Insufficient number of items in stock")
        }
        apply(ProductReservedEvent(command.productId, command.orderId, command.userId, command.quantity))
    }

    @EventSourcingHandler
    fun on(event: ProductReservedEvent) {
        quantity -= event.quantity
    }


    @CommandHandler
    fun handle(command: CancelProductReservationCommand) {
        apply(
            ProductReservationCancelledEvent(
                productId = command.productId,
                orderId = command.orderId,
                userId = command.userId,
                quantity = command.quantity,
                reason = command.reason
            )
        )
    }

    @EventSourcingHandler
    fun on(event: ProductReservationCancelledEvent) {
        quantity += event.quantity
    }
}