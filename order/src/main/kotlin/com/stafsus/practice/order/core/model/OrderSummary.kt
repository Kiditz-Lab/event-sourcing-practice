package com.stafsus.practice.order.core.model

import org.apache.commons.lang.StringUtils.EMPTY


data class OrderSummary(
    val orderId: String,
    val status: OrderStatus,
    val message: String = EMPTY
)