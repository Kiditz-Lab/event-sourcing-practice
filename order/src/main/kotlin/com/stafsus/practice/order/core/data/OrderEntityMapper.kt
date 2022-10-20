package com.stafsus.practice.order.core.data

import com.stafsus.practice.order.core.EntityMapper
import com.stafsus.practice.order.core.model.OrderSummary
import org.mapstruct.Mapper

@Mapper(config = EntityMapper::class)
interface OrderEntityMapper : EntityMapper<OrderEntity, OrderSummary>