package com.stafsus.payment.events

import com.stafsus.payment.core.data.PaymentEntity
import com.stafsus.payment.core.data.PaymentRepository
import com.stafsus.practice.core.events.PaymentProcessedEvent
import org.axonframework.eventhandling.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class PaymentEventsHandler(
    private val repository: PaymentRepository
) {
    companion object {
        private val log = LoggerFactory.getLogger(PaymentEventsHandler::class.java)
    }

    @EventHandler
    fun on(event: PaymentProcessedEvent) {
        log.info("PaymentProcessedEvent is called for orderId: " + event.orderId)
        val paymentEntity = PaymentEntity(event.paymentId, event.orderId)
        repository.save(paymentEntity)
    }
}