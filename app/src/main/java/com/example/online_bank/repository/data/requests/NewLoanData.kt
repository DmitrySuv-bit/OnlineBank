package com.example.online_bank.repository.data.requests

data class NewLoanData(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String
)