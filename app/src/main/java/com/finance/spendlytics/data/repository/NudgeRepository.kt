package com.finance.spendlytics.data.repository

import com.finance.spendlytics.data.model.Nudge

class NudgeRepository {
    fun getNudges(): List<Nudge> {
        return listOf(
            Nudge("You\'ve spent 80% of your grocery budget, and there are 10 days left in the month."),
            Nudge("You\'ve spent $50 on coffee this week.")
        )
    }
}