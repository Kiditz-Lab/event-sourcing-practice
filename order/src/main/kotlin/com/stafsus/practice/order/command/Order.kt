package com.stafsus.practice.order.command

import com.stafsus.practice.core.commands.RejectOrderCommand
import com.stafsus.practice.order.core.event.OrderApprovedEvent
import com.stafsus.practice.order.core.event.OrderCreatedEvent
import com.stafsus.practice.order.core.event.OrderRejectedEvent
import com.stafsus.practice.order.core.model.OrderStatus
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle.apply
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class Order {
    @AggregateIdentifier
    private lateinit var orderId: String
    private lateinit var productId: String
    private lateinit var userId: String
    private lateinit var addressId: String
    private var quantity: Int = 0
    private lateinit var status: OrderStatus

    constructor()

    @CommandHandler
    constructor(command: CreateOrderCommand) {
        apply(
            OrderCreatedEvent(
                command.orderId,
                command.productId,
                command.userId,
                command.quantity,
                command.addressId,
                command.status
            )
        )
    }

    @EventSourcingHandler
    fun on(event: OrderCreatedEvent) {
        orderId = event.orderId
        productId = event.productId
        userId = event.userId
        quantity = event.quantity
        addressId = event.addressId
        status = event.status
    }

    @CommandHandler
    fun handle(command: ApproveOrderCommand) {
        apply(OrderApprovedEvent(command.orderId))
    }

    @EventSourcingHandler
    fun on(event: OrderApprovedEvent) {
        status = event.status
    }
    @CommandHandler
    fun handle(command: RejectOrderCommand) {
        apply(OrderRejectedEvent(command.orderId, command.reason))
    }

    @EventSourcingHandler
    fun on(event: OrderRejectedEvent) {
        status = event.status
    }

}