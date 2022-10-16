package com.stafsus.practice.product.command

import com.stafsus.practice.product.core.data.ProductLookupRepository
import org.axonframework.commandhandling.CommandMessage
import org.axonframework.messaging.MessageDispatchInterceptor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class RegisterProductCommandInterceptor(
    private val lookupRepository: ProductLookupRepository
) : MessageDispatchInterceptor<CommandMessage<*>> {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun handle(messages: List<CommandMessage<*>>): BiFunction<Int, CommandMessage<*>, CommandMessage<*>> {
        return BiFunction { _: Int, command: CommandMessage<*> ->
            log.info("Interceptor command : ${command.payloadType}")
            if (RegisterProductCommand::class.java == command.payloadType) {
                val (productId, title) = command.payload as RegisterProductCommand
                val product = lookupRepository.findByIdOrTitle(productId, title)
                if(product != null){
                    throw IllegalStateException("Product with productId: $productId or title: $title already exists")
                }
            }
            command
        }
    }
}