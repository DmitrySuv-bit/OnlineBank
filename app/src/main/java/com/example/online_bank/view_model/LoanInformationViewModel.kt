package com.example.online_bank.view_model

import com.example.online_bank.repository.OnlineBankRepository
import com.example.online_bank.repository.data.response.LoanData
import io.reactivex.Observable

class LoanInformationViewModel(private val repository: OnlineBankRepository) {
    fun getLoanData(id: Int): Observable<LoanData> {
        return repository.getLoanData(id)
    }
}