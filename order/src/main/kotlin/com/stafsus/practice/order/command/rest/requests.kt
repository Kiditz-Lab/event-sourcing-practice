package com.stafsus.practice.order.command.rest

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CreateOrderRequest(
    @field:NotBlank
    val productId: String? = null,
    @field:NotNull
    @field:Min(1L)
    val quantity: Int? = null,
    @field:NotBlank
    val addressId: String? = null
)