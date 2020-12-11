package com.example.online_bank.view

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.doOnTextChanged
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.online_bank.App
import com.example.online_bank.R
import com.example.online_bank.repository.storage.AuthorizationKey
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginFragment : Fragment() {
    private val loginViewModel = App.injectLoginViewModel()
    private var disposable: Disposable? = null

    private var isName: Boolean = false
    private var isPassword: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun notif (view: View) {

        view.findViewById<Button>(R.id.loginButton).isEnabled = isName && isPassword
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<TextView>(R.id.loginName)
        val password = view.findViewById<TextView>(R.id.loginPassword)

//        name.doOnTextChanged { text, start, before, count ->
//
//            if (text?.length!! <= 3) {
//                isName = false
//                name.error = "hjbhb"
//            } else {
//                isName = true
//            }
//            notif(view)
//        }
//
//        password.doOnTextChanged { text, start, before, count ->
//
//            if (text?.length!! <= 3) {
//                isPassword = false
//            } else {
//                isPassword = true
//            }
//            notif(view)
//        }

        name.text = arguments?.let { LoginFragmentArgs.fromBundle(it).nameLogin }

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            logIn(view, name.text.toString(), password.text.toString())
        }

        view.findViewById<Button>(R.id.transitionRegistrationButton)?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registrationFragment))
    }

    private fun logIn(view: View, name: String, password: String) {
        disposable = loginViewModel.isLogin(name, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(
                    context,
                    "Вы вошли",
                    Toast.LENGTH_LONG
                ).show()

                view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }, {
                Toast.makeText(
                    context,
                    "Ошибка входа",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("otvet", it.toString())
            })
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}