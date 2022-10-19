package com.stafsus.practice.core.data

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "payments")
data class PaymentEntity(
    @Id
    var paymentId: String,
    @Column
    var orderId: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as PaymentEntity

        return paymentId == other.paymentId
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(paymentId = $paymentId , orderId = $orderId )"
    }
}