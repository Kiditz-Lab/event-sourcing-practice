package com.stafsus.practice.product.query.rest

import com.stafsus.practice.product.query.FindProductsQuery
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("/products")
class ProductQueryController(
    private val queryGateway: QueryGateway
) {
    @GetMapping
    fun getProducts(): CompletableFuture<MutableList<ProductResponse>> {
        val query = FindProductsQuery()
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ProductResponse::class.java))
    }
}