package com.stafsus.practice.product.core.data

import java.io.Serializable
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "product")
data class ProductEntity(
    @Id var id: String,
    @Column(unique = true) var title: String,
    var price: BigDecimal,
    var quantity: Int,
) : Serializable