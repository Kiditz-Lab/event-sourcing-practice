package com.stafsus.practice.order.configuration

import org.axonframework.config.ConfigurationScopeAwareProvider
import org.axonframework.deadline.DeadlineManager
import org.axonframework.deadline.SimpleDeadlineManager
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DeadlineManagerConfiguration {
    @Bean
    fun deadlineManager(
        configuration: org.axonframework.config.Configuration,
        transactionManager: SpringTransactionManager

    ): DeadlineManager {
        return SimpleDeadlineManager.builder()
            .scopeAwareProvider(ConfigurationScopeAwareProvider(configuration))
            .transactionManager(transactionManager)
            .build()
    }
}