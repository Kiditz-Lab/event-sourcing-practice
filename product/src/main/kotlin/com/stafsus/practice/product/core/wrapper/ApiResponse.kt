package com.stafsus.practice.product.core.wrapper

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.springframework.http.HttpStatus
import java.io.Serializable
import java.time.Instant

data class ApiResponse<T>(
    val status: HttpStatus = HttpStatus.OK,
    @field:JsonInclude(NON_NULL)
    val message: String? = null,
    @field:JsonInclude(NON_NULL)
    val errors: List<String>? = null,
    @field:JsonInclude(NON_NULL)
    val data: T? = null,
    @field:JsonInclude(NON_NULL)
    val type: String? = null,
    val timestamp: Instant = Instant.now()
) : Serializable