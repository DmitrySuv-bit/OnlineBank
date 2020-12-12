package com.example.online_bank

import android.app.Application
import android.content.Context
import com.example.online_bank.repository.OnlineBankRepository
import com.example.online_bank.repository.api.ApiService
import com.example.online_bank.repository.storage.AuthorizationKey
import com.example.online_bank.view_model.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class App : Application() {
    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var apiService: ApiService
        private lateinit var onlineBankRepository: OnlineBankRepository
        private lateinit var authorizationKey: AuthorizationKey
        private lateinit var homeViewModel: HomeViewModel
        private lateinit var loginViewModel: LoginViewModel
        private lateinit var newLoanViewModel: NewLoanViewModel
        private lateinit var registrationViewModel: RegistrationViewModel
        private lateinit var loanInformationViewModel: LoanInformationViewModel
        private lateinit var myContext: Context

        fun injectHomeViewModel() = homeViewModel
        fun injectLoginViewModel() = loginViewModel
        fun injectRegistrationViewModel() = registrationViewModel
        fun injectNewLoanViewModel() = newLoanViewModel
        fun injectLoanInformationViewModel() = loanInformationViewModel
    }

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.API_URL)
            .build()

        apiService = retrofit.create(ApiService::class.java)

        myContext = applicationContext

        authorizationKey = AuthorizationKey(myContext)
        onlineBankRepository = OnlineBankRepository(apiService, authorizationKey)

        homeViewModel = HomeViewModel(onlineBankRepository, authorizationKey)
        loginViewModel = LoginViewModel(onlineBankRepository)
        registrationViewModel = RegistrationViewModel(onlineBankRepository)
        newLoanViewModel = NewLoanViewModel(onlineBankRepository)
        loanInformationViewModel = LoanInformationViewModel(onlineBankRepository)
    }
}