package com.stafsus.practice.product.core

import org.mapstruct.*

@MapperConfig(
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    componentModel = "spring",
)
interface DomainMapper<T, U> {
    fun toDomain(source: U): T

    fun toModel(source: T): U
}