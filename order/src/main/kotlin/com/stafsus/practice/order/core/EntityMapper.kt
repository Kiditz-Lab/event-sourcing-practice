package com.stafsus.practice.order.core

import org.mapstruct.*

@MapperConfig(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    componentModel = "spring",
)
interface EntityMapper<E, S> {
    fun toEntity(source: S): E

    fun toModel(source: E): S
}