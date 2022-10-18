package com.stafsus.practice.order.command.rest

import com.stafsus.practice.order.command.CreateOrderCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderCommandController(
    private val commandGateway: CommandGateway
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createOrder(@RequestBody request: CreateOrderRequest): String {
        val command = CreateOrderCommand(
            productId = request.productId!!,
            quantity = request.quantity!!,
            addressId = request.addressId!!,
        )
        return commandGateway.sendAndWait(command)
    }
}