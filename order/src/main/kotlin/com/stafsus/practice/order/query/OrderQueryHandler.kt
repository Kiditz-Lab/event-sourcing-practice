package com.stafsus.practice.order.query

import com.stafsus.practice.order.core.data.OrderEntityMapper
import com.stafsus.practice.order.core.data.OrderRepository
import com.stafsus.practice.order.core.model.OrderSummary
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class OrderQueryHandler(
    private val repository: OrderRepository,
    private val mapper: OrderEntityMapper,
) {

    @QueryHandler
    fun getOrders(query: FindOrderQuery): OrderSummary {
        return repository.findById(query.orderId).map(mapper::toModel).orElse(null)
    }
}