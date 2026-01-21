package com.finance.spendlytics.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.finance.spendlytics.ui.screens.home.HomeViewModel

@Composable
fun HomeScreen(homeViewModel: HomeViewModel = viewModel()) {
    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item {
            // Joint View Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Joint View", style = MaterialTheme.typography.headlineSmall)
                Switch(
                    checked = homeViewModel.isJointView,
                    onCheckedChange = { homeViewModel.onJointViewToggle(it) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Smart Nudges
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Smart Nudges", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    homeViewModel.nudges.forEach {
                        Text(it.message)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Spending Summary
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = if (homeViewModel.isJointView) "Combined Spending" else "Individual Spending",
                        style = MaterialTheme.typography.titleMedium
                    )
                    // Placeholder for spending details
                    Text("$5,250.00")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // AI Financial Coach
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "AI Financial Coach", style = MaterialTheme.typography.titleMedium)
                    OutlinedTextField(
                        value = homeViewModel.question,
                        onValueChange = { homeViewModel.onQuestionChange(it) },
                        label = { Text("Ask a question") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { homeViewModel.askQuestion() },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Ask")
                    }
                    if (homeViewModel.answer.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(homeViewModel.answer)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Subscription Manager
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Subscription Manager", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    homeViewModel.subscriptions.forEach {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(it.name)
                            Text("%.2f".format(it.amount))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Gamified Goals
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Savings Jars", style = MaterialTheme.typography.titleMedium)
                    // Placeholder for savings goals
                    Text("Vacation Fund: $1,500 / $5,000")
                }
            }
        }
    }
}