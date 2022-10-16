package com.stafsus.practice.product.command

import org.apache.commons.lang.StringUtils.isBlank
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageDispatchInterceptor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.function.BiFunction

@Component
class RegisterProductCommandInterceptor : MessageDispatchInterceptor<CommandMessage<*>> {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun handle(messages: List<CommandMessage<*>>): BiFunction<Int, CommandMessage<*>, CommandMessage<*>> {
        return BiFunction { _: Int, command: CommandMessage<*> ->
            log.info("Interceptor command : ${command.payloadType}")
            if (RegisterProductCommand::class.java == command.payloadType) {
                val (_, title, price) = command.payload as RegisterProductCommand
                require(price > BigDecimal.ZERO) { "Price cannot be less or equal zero" }
                require(!isBlank(title)) { "Title cannot be empty" }
            }
            command
        }
    }
}