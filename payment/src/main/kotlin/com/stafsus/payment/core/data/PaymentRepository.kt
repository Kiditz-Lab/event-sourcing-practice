package com.stafsus.payment.core.data

import org.springframework.data.repository.CrudRepository

interface PaymentRepository : CrudRepository<PaymentEntity, String>