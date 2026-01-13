package com.example.spendlytics.data.repository

import com.example.spendlytics.data.model.Subscription
import java.util.Date

class SubscriptionRepository {
    fun getSubscriptions(): List<Subscription> {
        return listOf(
            Subscription("Netflix", 15.99, Date()),
            Subscription("Spotify", 9.99, Date()),
            Subscription("HBO Max", 14.99, Date())
        )
    }
}