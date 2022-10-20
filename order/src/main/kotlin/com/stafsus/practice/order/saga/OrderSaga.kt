package com.stafsus.practice.order.saga

import com.stafsus.practice.core.commands.CancelProductReservationCommand
import com.stafsus.practice.core.commands.ProcessPaymentCommand
import com.stafsus.practice.core.commands.RejectOrderCommand
import com.stafsus.practice.core.commands.ReserveProductCommand
import com.stafsus.practice.core.events.PaymentProcessedEvent
import com.stafsus.practice.core.events.ProductReservationCancelledEvent
import com.stafsus.practice.core.events.ProductReservedEvent
import com.stafsus.practice.core.model.User
import com.stafsus.practice.core.query.FetchUserPaymentDetailsQuery
import com.stafsus.practice.order.command.ApproveOrderCommand
import com.stafsus.practice.order.core.event.OrderApprovedEvent
import com.stafsus.practice.order.core.event.OrderCreatedEvent
import com.stafsus.practice.order.core.event.OrderRejectedEvent
import com.stafsus.practice.order.core.model.OrderSummary
import com.stafsus.practice.order.query.FindOrderQuery
import org.apache.commons.lang.StringUtils.EMPTY
import org.axonframework.commandhandling.CommandCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.deadline.DeadlineManager
import org.axonframework.deadline.annotation.DeadlineHandler
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.queryhandling.QueryUpdateEmitter
import org.axonframework.spring.stereotype.Saga
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*

private const val PAYMENT_PROCESSING_DEADLINE = "payment-processing-deadline"

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

    @Autowired
    @Transient
    private lateinit var queryUpdateEmitter: QueryUpdateEmitter

    @Autowired
    @Transient
    private lateinit var deadlineManager: DeadlineManager


    private var scheduleId: String = EMPTY

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: OrderCreatedEvent) {
        val command = ReserveProductCommand(
            productId = event.productId, orderId = event.orderId, userId = event.userId, quantity = event.quantity
        )
        log.info("OrderCreatedEvent is called for orderId: ${command.orderId}, productId: ${command.productId}")
        commandGateway.send(command, CommandCallback<ReserveProductCommand, Any> { _, commandResultMessage ->
            if (commandResultMessage.isExceptional) {
                commandGateway.send<String>(
                    RejectOrderCommand(
                        event.orderId,
                        commandResultMessage.exceptionResult().message!!
                    )
                )
            }
        })
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: ProductReservedEvent) {
        log.info("ProductReservedEvent is called for orderId: ${event.orderId}, productId: ${event.productId}")
        val query = FetchUserPaymentDetailsQuery(event.userId)
        val userPaymentDetails: User = try {
            queryGateway.query(query, ResponseTypes.instanceOf(User::class.java)).join()
        } catch (ex: Exception) {
            cancelProductReservation(event, ex.message ?: "Could not fetch user payment details")
            log.error(ex.message)
            null
        } ?: return

        log.info("Successfully fetch user payment details for user : ${userPaymentDetails.firstName}")
        val duration = Duration.of(120, ChronoUnit.MINUTES)
        deadlineManager.schedule(duration, PAYMENT_PROCESSING_DEADLINE, event)

        val processPaymentCommand = ProcessPaymentCommand(
            orderId = event.orderId,
            paymentDetails = userPaymentDetails.paymentDetails,
            paymentId = UUID.randomUUID().toString(),
        )
        try {
            commandGateway.sendAndWait(processPaymentCommand)
        } catch (e: Exception) {
            log.error(e.message)
            cancelProductReservation(event, e.message ?: "Could not process payment with user payment details")
        }
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: PaymentProcessedEvent) {
        cancelDeadline()
        log.info("PaymentProcessedEvent is called for orderId: ${event.orderId}")
        commandGateway.send<String>(ApproveOrderCommand(event.orderId))
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: OrderApprovedEvent) {
        log.info("OrderApprovedEvent is complete for orderId: ${event.orderId}")
        queryUpdateEmitter.emit(FindOrderQuery::class.java, { true }, OrderSummary(event.orderId, event.status))
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: OrderRejectedEvent) {
        log.info("Successfully rejected order with orderId: ${event.orderId}")
        queryUpdateEmitter.emit(
            FindOrderQuery::class.java,
            { true },
            OrderSummary(event.orderId, event.status, event.reason)
        )
    }

    @SagaEventHandler(associationProperty = "orderId")
    fun handle(event: ProductReservationCancelledEvent) {
        log.info("ProductReservationCancelledEvent is complete for orderId: ${event.orderId}")
        commandGateway.send<String>(
            RejectOrderCommand(
                orderId = event.orderId,
                reason = event.reason
            )
        )
    }

    @DeadlineHandler(deadlineName = PAYMENT_PROCESSING_DEADLINE)
    fun handlePaymentDeadline(event: ProductReservedEvent) {
        log.info("Payment processing deadline took place. Sending payment processing command to cancel product reservation.")
        cancelProductReservation(event, "Payment timeout")
    }

    fun cancelProductReservation(event: ProductReservedEvent, reason: String) {
        val command = CancelProductReservationCommand(
            productId = event.productId,
            orderId = event.orderId,
            quantity = event.quantity,
            userId = event.userId,
            reason = reason
        )
        commandGateway.send<String>(command)
    }


    private fun cancelDeadline() {
        if (scheduleId != EMPTY) {
            deadlineManager.cancelSchedule(PAYMENT_PROCESSING_DEADLINE, scheduleId)
            scheduleId = EMPTY
        }
    }


}