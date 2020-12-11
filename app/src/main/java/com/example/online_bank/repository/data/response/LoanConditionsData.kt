package com.example.online_bank.repository.data.response

data class LoanConditionsData(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
)