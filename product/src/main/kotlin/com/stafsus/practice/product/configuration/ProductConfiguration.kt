package com.stafsus.practice.product.configuration

import com.stafsus.practice.product.command.RegisterProductCommandInterceptor
import org.axonframework.commandhandling.CommandBus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration

@Configuration
class ProductConfiguration {
    @Autowired
    fun registerInterceptors(commandBus: CommandBus, context: ApplicationContext) {
        commandBus.registerDispatchInterceptor(context.getBean(RegisterProductCommandInterceptor::class.java))
    }
}