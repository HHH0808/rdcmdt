package com.example.cupcake.network

import java.security.Timestamp
import java.util.*

data class TransactionData(
    val transactionId: String,
    val amount: String,
    val transactionDate: String,
    val description: String,
    val transactionType: String,
    val receipient: TransactionSender?,
    val sender: TransactionSender?
)
