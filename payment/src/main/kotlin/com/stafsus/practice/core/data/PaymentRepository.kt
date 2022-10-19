package com.stafsus.practice.core.data

import org.springframework.data.repository.CrudRepository

interface PaymentRepository : CrudRepository<PaymentEntity, String>