package com.stafsus.practice.product.command.rest

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class RegisterProductRequest(
    @field:NotBlank
    val title: String? = "",
    @field:Min(value = 1L)
    val price: BigDecimal,
    @field:Min(value = 1L)
    val quantity: Int
)