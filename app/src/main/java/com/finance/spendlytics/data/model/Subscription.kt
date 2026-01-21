package com.finance.spendlytics.data.model

import java.util.Date

data class Subscription(
    val name: String,
    val amount: Double,
    val nextPaymentDate: Date
)