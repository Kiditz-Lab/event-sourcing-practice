package com.stafsus.practice.product.command.rest

import com.stafsus.practice.product.command.RegisterProductCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.core.env.Environment
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/products")
class ProductCommandController(
    private val env: Environment,
    private val commandGateway: CommandGateway
) {

    @PostMapping
    fun registerProduct(@RequestBody request: RegisterProductRequest): String {
        val command = RegisterProductCommand(
            title = request.title,
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

    @PutMapping
    fun updateProduct(): String {
        return "Http Put Handled"
    }
//
//    @GetMapping
//    fun getProducts(): String {
//        return "Http Get Handled: ${env.getProperty("local.server.port")}"
//    }

    @DeleteMapping
    fun deleteProduct(): String {
        return "Http Delete Handled"
    }

}