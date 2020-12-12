package com.example.online_bank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.online_bank.App
import com.example.online_bank.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginFragment : Fragment() {
    private companion object {
        const val NAME_KEY = "com.example.online_bank.view.name"
    }

    private val loginViewModel = App.injectLoginViewModel()
    private var disposable: Disposable? = null

    private val name: TextView?
        get() = view?.findViewById(R.id.loginName)

    private val password: TextView?
        get() = view?.findViewById(R.id.loginPassword)

    private val loginButton: Button?
        get() = view?.findViewById(R.id.loginButton)

    private var isNameInputCondition: Boolean = false
    private var isPasswordInputCondition: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name?.text = arguments?.let { LoginFragmentArgs.fromBundle(it).nameLogin }

        isNameInputCondition = name?.text?.length!! >= 1

        allowDispatch()

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            logIn()
        }

        view.findViewById<Button>(R.id.transitionRegistrationButton)?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registrationFragment)
        )

        name?.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 1) {
                name?.error = "Заполните поле"

                isNameInputCondition = false
            } else {
                isNameInputCondition = true
            }

            allowDispatch()
        }

        password?.doOnTextChanged { text, _, _, _ ->
            if (text?.length!! < 1) {
                password?.error = "Заполните поле"

                isPasswordInputCondition = false
            } else {
                isPasswordInputCondition = true
            }

            allowDispatch()
        }
    }

    private fun logIn() {
        disposable = loginViewModel.isLogin(name?.text.toString(), password?.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view?.findNavController()?.navigate(R.id.action_loginFragment_to_homeFragment)
            }, {
                password?.text = ""
                password?.error = "Неверный логин или пароль"
            })
    }

    private fun allowDispatch() {
        loginButton?.isEnabled =
            isNameInputCondition && isPasswordInputCondition
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}