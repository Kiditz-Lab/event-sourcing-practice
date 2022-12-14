package com.stafsus.practice.product.query

import com.stafsus.practice.product.core.data.ProductLookupEntity
import com.stafsus.practice.product.core.data.ProductLookupRepository
import com.stafsus.practice.product.core.event.ProductRegisteredEvent
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.ResetHandler
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("product-group")
class ProductLookupEventHandler(
    private val repository: ProductLookupRepository
) {
    @EventHandler
    fun on(event: ProductRegisteredEvent) {
        val lookup = ProductLookupEntity(
            id = event.productId,
            title = event.title,
        )
        repository.save(lookup)
    }

    @ResetHandler
    fun resetProduct() {
        repository.deleteAll()
    }
}