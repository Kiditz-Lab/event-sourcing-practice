package com.stafsus.practice.core.query

import com.stafsus.practice.core.model.PaymentDetails
import com.stafsus.practice.core.model.User
import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class UserEventsHandler {
    @QueryHandler
    fun findUser(query: FetchUserPaymentDetailsQuery): User {
        return User(
            userId = query.userId,
            firstName = "Rifky",
            lastName = "Aditya Bastara",
            paymentDetails = PaymentDetails(
                cardNumber = "0123",
                name = "Rifky Aditya",
                validUntilMonth = 12,
                validUntilYear = 2030,
                cvv = "123"
            )
        )
    }
}