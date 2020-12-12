package com.example.online_bank.view_model

import com.example.online_bank.repository.OnlineBankRepository
import io.reactivex.Observable

class LoginViewModel(private val repository: OnlineBankRepository) {
    fun isLogin(name: String, password: String): Observable<Boolean> {
        return repository.postLogin(name, password)
    }
}