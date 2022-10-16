package com.stafsus.practice.product.query

import com.stafsus.practice.product.core.EntityMapper
import com.stafsus.practice.product.core.data.ProductEntity
import com.stafsus.practice.product.query.rest.ProductResponse
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(config = EntityMapper::class)
interface ProductEntityMapper : EntityMapper<ProductEntity, ProductResponse> {
    @Mapping(source = "id", target = "productId")
    override fun toModel(source: ProductEntity): ProductResponse
}