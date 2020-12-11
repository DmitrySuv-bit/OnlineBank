package com.example.online_bank.view_model

import com.example.online_bank.repository.OnlineBankRepository
import com.example.online_bank.repository.data.requests.NewLoanData
import com.example.online_bank.repository.data.response.LoanConditionsData
import com.example.online_bank.repository.data.response.LoanData
import io.reactivex.Observable

class NewLoanViewModel(private val repository: OnlineBankRepository) {
    fun getLoanConditions(): Observable<LoanConditionsData> {
        return repository.getLoanConditions()
    }

    fun postLoans(amount: Int, firstName: String, lastName: String, percent: Double, period: Int, phoneNumber: String): Observable<LoanData> {
        return repository.postLoans(amount, firstName, lastName, percent, period, phoneNumber)
    }
}