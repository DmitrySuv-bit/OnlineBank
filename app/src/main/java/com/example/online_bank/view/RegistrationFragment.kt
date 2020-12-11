package com.example.online_bank.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.online_bank.App
import com.example.online_bank.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegistrationFragment : Fragment() {
    private val registrationViewModel = App.injectRegistrationViewModel()
    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = view.findViewById<TextView>(R.id.registrationName)

        val password = view.findViewById<TextView>(R.id.registrationPassword)

        view.findViewById<Button>(R.id.registrationButton).setOnClickListener {

           register(view, name.text.toString(), password.text.toString())
        }
    }

    private fun register(view: View, name: String, password: String) {
        disposable = registrationViewModel.postRegistration(name, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(
                    context,
                    "Успешная регистрация",
                    Toast.LENGTH_LONG
                ).show()

                view.findNavController().navigate(RegistrationFragmentDirections.actionRegistrationFragmentToLoginFragment(it.name))
            }, {
                Toast.makeText(
                    context,
                    "Ошибка регистрации",
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