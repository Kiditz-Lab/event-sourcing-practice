package com.stafsus.practice.order.core.data

import com.stafsus.practice.order.core.EntityMapper
import com.stafsus.practice.order.query.rest.OrderResponse
import org.mapstruct.Mapper

@Mapper(config = EntityMapper::class)
interface OrderEntityMapper : EntityMapper<OrderEntity, OrderResponse>