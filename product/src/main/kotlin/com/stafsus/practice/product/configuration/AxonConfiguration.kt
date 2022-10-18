package com.stafsus.practice.product.configuration

import com.stafsus.practice.product.command.RegisterProductCommandInterceptor
import org.axonframework.commandhandling.CommandBus
import org.axonframework.config.EventProcessingConfigurer
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfiguration() {
    @Autowired
    fun registerInterceptors(commandBus: CommandBus) {
        commandBus.registerDispatchInterceptor(BeanValidationInterceptor())
    }

    @Autowired
    fun registerProductCommandInterceptor(commandBus: CommandBus, context: ApplicationContext) {
        commandBus.registerDispatchInterceptor(context.getBean(RegisterProductCommandInterceptor::class.java))
    }

    @Autowired
    fun configure(config: EventProcessingConfigurer) {
        config.registerListenerInvocationErrorHandler("product-group") { ProductEventErrorHandler() }
    }
}