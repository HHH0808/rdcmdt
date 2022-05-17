package com.example.cupcake.network

data class TransferFund(
    val status: String,
    val transactionId: String,
    val amount: Double,
    val description: String,
    val receipientAccount: String
)
