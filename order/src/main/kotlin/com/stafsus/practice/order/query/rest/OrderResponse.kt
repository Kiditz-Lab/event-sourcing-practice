package com.stafsus.practice.order.query.rest

import com.stafsus.practice.order.command.OrderStatus
import com.stafsus.practice.order.core.model.OrderStatus
import org.hibernate.Hibernate
import javax.persistence.*

data class OrderResponse(
    val orderId: String,
    var productId: String,
    var userid: String,
    var addressid: String,
    var quantity: Int,
    var status: OrderStatus
)