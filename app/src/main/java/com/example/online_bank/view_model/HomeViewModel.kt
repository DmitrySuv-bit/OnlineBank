package com.example.online_bank.view_model

import com.example.online_bank.repository.OnlineBankRepository
import com.example.online_bank.repository.data.response.LoansListItem
import com.example.online_bank.repository.storage.AuthorizationKey
import io.reactivex.Observable

class HomeViewModel(
    private val repository: OnlineBankRepository,
    private val token: AuthorizationKey
) {
    fun getLoansList(): Observable<List<LoansListItem>> {
        return repository.getLoansList()
    }

    fun isAuthorization(): Boolean {
        if (token.getKey() == "") {
            return false
        }

        return true
    }

    fun logOut() {
        token.setKey("")
    }
}