package com.stafsus.practice.core.model

data class PaymentDetails(
    val name: String,
    val cardNumber: String,
    val validUntilMonth: Int,
    val validUntilYear: Int,
    val cvv: String,
)