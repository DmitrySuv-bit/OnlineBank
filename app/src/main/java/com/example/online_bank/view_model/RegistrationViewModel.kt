package com.example.online_bank.view_model

import com.example.online_bank.repository.OnlineBankRepository
import com.example.online_bank.repository.data.response.RegistrationData
import io.reactivex.Observable

class RegistrationViewModel(private val repository: OnlineBankRepository) {
    fun postRegistration(name: String, password: String): Observable<RegistrationData> {
        return repository.postRegistration(name, password)
    }
}