package com.stafsus.practice.order.command.rest

import com.stafsus.practice.order.command.CreateOrderCommand
import com.stafsus.practice.order.core.model.OrderSummary
import com.stafsus.practice.order.query.FindOrderQuery
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryGateway
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping("/orders")
class OrderCommandController(
    private val commandGateway: CommandGateway,
    private val queryGateway: QueryGateway,
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@RequestBody request: CreateOrderRequest): OrderSummary {
        val command = CreateOrderCommand(
            productId = request.productId!!,
            quantity = request.quantity!!,
            addressId = request.addressId!!,
        )
        val queryResult = queryGateway.subscriptionQuery(
            FindOrderQuery(command.orderId),
            ResponseTypes.instanceOf(OrderSummary::class.java),
            ResponseTypes.instanceOf(OrderSummary::class.java)
        )
        queryResult.use { result ->
            commandGateway.sendAndWait<String>(command)
            return result.updates().blockFirst() ?: throw IllegalArgumentException("order not found")
        }
    }
}