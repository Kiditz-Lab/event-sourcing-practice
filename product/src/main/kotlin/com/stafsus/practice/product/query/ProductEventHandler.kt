package com.stafsus.practice.product.query

import com.stafsus.practice.product.core.data.ProductEntity
import com.stafsus.practice.product.core.data.ProductRepository
import com.stafsus.practice.product.core.event.ProductRegisteredEvent
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class ProductEventHandler(
    private val repository: ProductRepository
) {
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
}