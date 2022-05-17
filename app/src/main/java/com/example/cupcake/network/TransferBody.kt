package com.example.cupcake.network

data class TransferBody(
    val receipientAccountNo: String,
    val amount: Double,
    val description: String
)
