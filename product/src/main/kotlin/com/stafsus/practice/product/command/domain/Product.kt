package com.stafsus.practice.product.command.domain

import com.stafsus.practice.product.command.RegisterProductCommand
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
}