package com.stafsus.practice.product

import com.stafsus.practice.product.configuration.XStreamConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Import

@EnableDiscoveryClient
@SpringBootApplication
@Import(XStreamConfig::class)
class ProductApplication

fun main(args: Array<String>) {
    runApplication<ProductApplication>(*args)
}
