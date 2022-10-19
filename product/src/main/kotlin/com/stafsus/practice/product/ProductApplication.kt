package com.stafsus.practice.product

import com.stafsus.practice.core.configuration.AxonConfiguration
import com.stafsus.practice.core.configuration.XStreamConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Import

@EnableDiscoveryClient
@SpringBootApplication
@Import(value = [XStreamConfig::class, AxonConfiguration::class])
class ProductApplication

fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}
