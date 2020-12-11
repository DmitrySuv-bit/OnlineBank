package com.example.online_bank.repository.api

import com.example.online_bank.repository.data.requests.LoginAndRegistrationData
import com.example.online_bank.repository.data.requests.NewLoanData
import com.example.online_bank.repository.data.response.*
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {
    @POST("login")
    fun postLogin(
        @Body data: LoginAndRegistrationData
    ): Observable<String>

    @POST("registration")
    fun postRegistration(
        @Body data: LoginAndRegistrationData
    ): Observable<RegistrationData>

    @POST("loans")
    fun postLoans(
        @Header("Authorization") auth: String,
        @Body data: NewLoanData
    ): Observable<LoanData>

    @GET("loans/{id}")
    fun getLoanData(
        @Path("id") id: Int,
        @Header("Authorization") auth: String
    ): Observable<LoanData>

    @GET("loans/conditions")
    fun getLoanConditions(
        @Header("Authorization") auth: String
    ): Observable<LoanConditionsData>

    @GET("loans/all")
    fun getLoansList(
        @Header("Authorization") auth: String
    ): Observable<List<LoansListItem>>
}