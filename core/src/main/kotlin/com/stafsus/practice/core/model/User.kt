package com.stafsus.practice.core.model

data class User(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val paymentDetails: PaymentDetails,
)