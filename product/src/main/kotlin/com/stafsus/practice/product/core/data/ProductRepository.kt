package com.stafsus.practice.product.core.data

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, String> {
}