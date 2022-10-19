package com.stafsus.practice.order.saga

import com.stafsus.practice.core.commands.ProcessPaymentCommand
import com.stafsus.practice.core.commands.ReserveProductCommand
import com.stafsus.practice.core.events.ProductReservedEvent
import com.stafsus.practice.core.model.User
import com.stafsus.practice.core.query.FetchUserPaymentDetailsQuery
import com.stafsus.practice.order.core.event.OrderCreatedEvent
import org.apache.commons.lang.StringUtils
import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.spring.stereotype.Saga
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import java.util.concurrent.TimeUnit

@Saga
class OrderSaga {

    companion object {
        private val log = LoggerFactory.getLogger(OrderSaga::class.java)
    }

    @Autowired
    @Transient
    private lateinit var commandGateway: CommandGateway

    @Autowired
    @Transient
    private lateinit var queryGateway: QueryGateway

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: OrderCreatedEvent) {
        val command = ReserveProductCommand(
            productId = event.productId, orderId = event.orderId, userId = event.userId, quantity = event.quantity
        )
        log.info("OrderCreatedEvent is called for orderId: ${command.orderId}, productId: ${command.productId}")
        commandGateway.send(command, LoggingCallback.INSTANCE)
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: ProductReservedEvent) {
        log.info("ProductReservedEvent is called for orderId: ${event.orderId}, productId: ${event.productId}")
        val query = FetchUserPaymentDetailsQuery(event.userId)
        val userPaymentDetails: User = try {
            queryGateway.query(query, ResponseTypes.instanceOf(User::class.java)).join()
        } catch (ex: Exception) {
            log.error(ex.message)
            null
        } ?: return

        log.info("Successfully fetch payment details for user : ${userPaymentDetails.firstName}")
        val paymentCommand = ProcessPaymentCommand(
            orderId = event.orderId,
            paymentDetails = userPaymentDetails.paymentDetails,
            paymentId = UUID.randomUUID().toString(),
        )
        val result: String = try {
            commandGateway.sendAndWait(paymentCommand, 10, TimeUnit.SECONDS)
        } catch (e: Exception) {
            StringUtils.EMPTY
        }
    }


//    @EndSaga
//    @SagaEventHandler(associationProperty = "orderId")
//    fun handle(event: OrderRejectedEvent) {
//        log.info("OrderRejectedEvent is called for orderId: ${event.orderId}")
//    }
}