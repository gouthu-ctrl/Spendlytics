package com.example.spendlytics.ui.screens.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.finance.spendlytics.R
import com.finance.spendlytics.ui.theme.DarkSurface
import com.finance.spendlytics.ui.theme.TextSecondary

// Data classes to model our screen's data
data class Transaction(val id: Int, val category: String, val amount: Double, val vendor: String, val iconRes: Int)

// Dummy data for the recent transactions
val recentTransactions = listOf(
    Transaction(1, "Restaurants", 71.50, "The Corner Bistro", R.drawable.ic_restaurants),
    Transaction(2, "Groceries", 124.20, "Fresh Market", R.drawable.ic_groceries),
    Transaction(3, "Movies", 35.00, "Cineplex", R.drawable.ic_movies),
    Transaction(4, "Groceries", 45.10, "Quick Mart", R.drawable.ic_groceries),
)

@Composable
fun OverviewScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // --- Overview Section ---
        item {
            Text("Overview", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = DarkSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Last 5 Days", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    // Placeholder for a clean chart
                    Text("//TODO: Implement a clean, modern chart here", color = TextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- Recent Transactions Section ---
        item {
            Text("Recent", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(recentTransactions) { transaction ->
            TransactionItem(transaction)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkSurface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = transaction.iconRes),
                    contentDescription = transaction.category,
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Column {
                    Text(transaction.vendor, fontWeight = FontWeight.SemiBold)
                    Text(transaction.category, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
            }
            Text("$${String.format("%.2f", transaction.amount)}", fontWeight = FontWeight.Bold)
        }
    }
}