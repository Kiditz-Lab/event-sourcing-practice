package com.stafsus.practice.order.core.data

import com.stafsus.practice.order.core.model.OrderStatus
import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "orders")
data class OrderEntity(
    @Id
    @Column(unique = true)
    var orderId: String,
    var productId: String,
    var userid: String,
    var addressid: String,
    var quantity: Int,
    var status: OrderStatus
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as OrderEntity

        return orderId == other.orderId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(orderId = $orderId )"
    }
}