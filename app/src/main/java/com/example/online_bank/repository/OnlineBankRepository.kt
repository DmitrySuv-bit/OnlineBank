package com.example.online_bank.repository

import com.example.online_bank.repository.api.ApiService
import com.example.online_bank.repository.data.requests.LoginAndRegistrationData
import com.example.online_bank.repository.data.requests.NewLoanData
import com.example.online_bank.repository.data.response.*
import com.example.online_bank.repository.storage.AuthorizationKey
import io.reactivex.Observable

class OnlineBankRepository(
    private val apiService: ApiService,
    private val token: AuthorizationKey
) {
    fun postLogin(name: String, password: String): Observable<Boolean> {
        return apiService.postLogin(LoginAndRegistrationData(name, password))
            .map {
                token.setKey(it)
                it.isNotBlank()
            }
    }

    fun postRegistration(name: String, password: String): Observable<RegistrationData> {
        return apiService.postRegistration(LoginAndRegistrationData(name, password))
    }

    fun postLoans(amount: Int, firstName: String, lastName: String, percent: Double, period: Int, phoneNumber: String): Observable<LoanData> {
        return apiService.postLoans(token.getKey()!!, NewLoanData(amount, firstName, lastName, percent, period, phoneNumber))
    }

    fun getLoanData(id: Int): Observable<LoanData> {
        return apiService.getLoanData(id, token.getKey()!!)
    }

    fun getLoansList(): Observable<List<LoansListItem>> {
        return apiService.getLoansList(token.getKey()!!)
    }

    fun getLoanConditions() : Observable<LoanConditionsData> {
        return apiService.getLoanConditions(token.getKey()!!)
    }
}