package com.example.online_bank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
import com.example.online_bank.App
import com.example.online_bank.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegistrationFragment : Fragment() {
    private val registrationViewModel = App.injectRegistrationViewModel()
    private var disposable: Disposable? = null

    private val name: TextView?
        get() = view?.findViewById(R.id.registrationName)

    private val password: TextView?
        get() = view?.findViewById(R.id.registrationPassword)

    private val registrationButton: Button?
        get() = view?.findViewById(R.id.registrationButton)

    private var isNameInputCondition: Boolean = false
    private var isPasswordInputCondition: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationButton?.setOnClickListener {
            register()
        }

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

    private fun register() {
        disposable =
            registrationViewModel.postRegistration(name?.text.toString(), password?.text.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.findNavController()?.navigate(
                        RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment(it.name)
                    )
                }, {
                    password?.text = ""
                    password?.error = "Неверный логин или пароль"
                })
    }

    private fun allowDispatch() {
        registrationButton?.isEnabled =
            isNameInputCondition && isPasswordInputCondition
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}