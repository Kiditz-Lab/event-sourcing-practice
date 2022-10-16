package com.stafsus.practice.product.command.rest

import com.stafsus.practice.product.command.RegisterProductCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/products")
class ProductCommandController(
    private val commandGateway: CommandGateway
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerProduct(@Valid @RequestBody request: RegisterProductRequest): String {
        val command = RegisterProductCommand(
            title = request.title!!,
            price = request.price,
            quantity = request.quantity,
            productId = UUID.randomUUID().toString()
        )
        val returnValue = try {
            commandGateway.sendAndWait(command)
        } catch (e: Exception) {
            e.localizedMessage
        }
        return returnValue
    }

}