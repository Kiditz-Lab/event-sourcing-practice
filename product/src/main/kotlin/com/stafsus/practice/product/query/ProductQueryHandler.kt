package com.stafsus.practice.product.query

import com.stafsus.practice.product.core.data.ProductEntityMapper
import com.stafsus.practice.product.core.data.ProductRepository
import com.stafsus.practice.product.query.rest.ProductResponse
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class ProductQueryHandler(
    private val repository: ProductRepository,
    private val mapper: ProductEntityMapper,
) {

    @QueryHandler
    fun findProducts(query: FindProductsQuery): List<ProductResponse> {
        return repository.findAll().map(mapper::toModel)
    }
}