package com.stafsus.practice.order.saga

import com.stafsus.practice.core.commands.ReserveProductCommand
import com.stafsus.practice.core.events.ProductReservedEvent
import com.stafsus.practice.order.core.event.OrderCreatedEvent
import com.stafsus.practice.order.core.event.OrderRejectedEvent
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

@Saga
class OrderSaga {

    private val log = LoggerFactory.getLogger(javaClass)

    @Autowired
    @Transient
    private lateinit var commandGateway: CommandGateway

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: OrderCreatedEvent) {
        val command = ReserveProductCommand(
            productId = event.productId,
            orderId = event.orderId,
            userId = event.userId,
            quantity = event.quantity
        )
        log.info("OrderCreatedEvent is called for orderId: ${command.orderId}, productId: ${command.productId}")
        commandGateway.send(command, LoggingCallback.INSTANCE)
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: ProductReservedEvent) {
        log.info("ProductReservedEvent is called for orderId: ${event.orderId}, productId: ${event.productId}")
    }


    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: OrderRejectedEvent) {
        log.info("OrderRejectedEvent is called for orderId: ${event.orderId}")
    }
}