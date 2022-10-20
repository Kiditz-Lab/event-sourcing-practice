package com.stafsus.practice.product.query

import com.stafsus.practice.core.events.ProductReservationCancelledEvent
import com.stafsus.practice.core.events.ProductReservedEvent
import com.stafsus.practice.product.core.data.ProductEntity
import com.stafsus.practice.product.core.data.ProductRepository
import com.stafsus.practice.product.core.event.ProductRegisteredEvent
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("product-group")
class ProductEventHandler(
    private val repository: ProductRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    @EventHandler
    fun on(event: ProductRegisteredEvent) {
        val product = ProductEntity(
            id = event.productId,
            title = event.title,
            price = event.price,
            quantity = event.quantity
        )
        repository.save(product)
    }

    @EventHandler
    fun on(event: ProductReservedEvent) {
        val product = repository.findById(event.productId).orElse(null) ?: return
        repository.save(product.copy(quantity = product.quantity - event.quantity))
        log.info("ProductReservedEvent is called for productId: ${event.productId} and orderId: ${event.orderId}")
    }

    @EventHandler
    fun on(event: ProductReservationCancelledEvent) {
        val product = repository.findById(event.productId).orElse(null) ?: return
        repository.save(product.copy(quantity = product.quantity + event.quantity))
        log.info("ProductReservationCancelledEvent is called for productId: ${event.productId} and orderId: ${event.orderId}")
    }
}