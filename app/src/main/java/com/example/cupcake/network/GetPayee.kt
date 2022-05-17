package com.example.cupcake.network

data class GetPayee(
    val status: String,
    val data: List<PayeeData>
)
