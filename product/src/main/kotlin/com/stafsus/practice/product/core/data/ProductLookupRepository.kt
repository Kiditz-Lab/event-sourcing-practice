package com.stafsus.practice.product.core.data

import org.springframework.data.repository.CrudRepository

interface ProductLookupRepository : CrudRepository<ProductLookupEntity, String> {
    fun findByIdOrTitle(id: String, title: String): ProductLookupEntity?
}