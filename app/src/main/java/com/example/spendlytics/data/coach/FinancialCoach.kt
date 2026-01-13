package com.example.spendlytics.data.coach

class FinancialCoach {
    fun getAnswer(question: String): String {
        return when {
            question.contains("afford") -> "Based on your current spending, you can afford it."
            question.contains("save") -> "You can save more by cutting back on dining out."
            else -> "I'm sorry, I can't answer that question yet."
        }
    }
}