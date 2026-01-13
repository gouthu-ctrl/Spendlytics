package com.example.spendlytics.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.spendlytics.data.coach.FinancialCoach
import com.example.spendlytics.data.model.Nudge
import com.example.spendlytics.data.model.Subscription
import com.example.spendlytics.data.repository.NudgeRepository
import com.example.spendlytics.data.repository.SubscriptionRepository

class HomeViewModel : ViewModel() {
    var isJointView by mutableStateOf(false)
        private set

    var question by mutableStateOf("")
        private set

    var answer by mutableStateOf("")
        private set

    var subscriptions by mutableStateOf<List<Subscription>>(emptyList())
        private set

    var nudges by mutableStateOf<List<Nudge>>(emptyList())
        private set

    private val financialCoach = FinancialCoach()
    private val subscriptionRepository = SubscriptionRepository()
    private val nudgeRepository = NudgeRepository()

    init {
        getSubscriptions()
        getNudges()
    }

    fun onJointViewToggle(isJoint: Boolean) {
        isJointView = isJoint
    }

    fun onQuestionChange(newQuestion: String) {
        question = newQuestion
    }

    fun askQuestion() {
        answer = financialCoach.getAnswer(question)
    }

    private fun getSubscriptions() {
        subscriptions = subscriptionRepository.getSubscriptions()
    }

    private fun getNudges() {
        nudges = nudgeRepository.getNudges()
    }
}