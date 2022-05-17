package com.example.cupcake.network

data class PayeeData(
    val id: String,
    val name: String,
    val accountNo: String
){
    override fun toString(): String {
        return name
    }
}