package com.example.cupcake.network

import java.util.*

data class UserTransaction (
    val status: String,
    val data: List<TransactionData>,
)