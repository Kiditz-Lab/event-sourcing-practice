package com.stafsus.practice.product.core.exception

import com.stafsus.practice.product.core.wrapper.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalErrorHandler {

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(ex: IllegalStateException) = ResponseEntity(
        ApiResponse<Unit>(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = ex.localizedMessage,
            type = ex.javaClass.simpleName,
        ), HttpStatus.INTERNAL_SERVER_ERROR
    )

    @ExceptionHandler(Exception::class)
    fun handleOtherException(ex: Exception) = ResponseEntity(
        ApiResponse<Unit>(
            status = HttpStatus.INTERNAL_SERVER_ERROR,
            message = ex.localizedMessage,
            type = ex.javaClass.simpleName,
        ), HttpStatus.INTERNAL_SERVER_ERROR
    )
}