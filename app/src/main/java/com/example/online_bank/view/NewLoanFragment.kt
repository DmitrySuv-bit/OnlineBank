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

class NewLoanFragment : Fragment() {
    private val newLoanViewModel = App.injectNewLoanViewModel()
    private var disposable: Disposable? = null

    private val maxAmount: TextView?
        get() = view?.findViewById(R.id.newLoanMaxAmount)

    private val percent: TextView?
        get() = view?.findViewById(R.id.newLoanPercent)

    private val period: TextView?
        get() = view?.findViewById(R.id.newLoanPeriod)

    private val firstName: TextView?
        get() = view?.findViewById(R.id.newLoanFirstName)

    private val lastName: TextView?
        get() = view?.findViewById(R.id.newLoanLastName)

    private val phone: TextView?
        get() = view?.findViewById(R.id.newLoanPhone)

    private val amount: TextView?
        get() = view?.findViewById(R.id.newLoanAmount)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_loan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getLoanCondition()

        view.findViewById<Button>(R.id.newLoanButton).setOnClickListener {
            getNewLoan(view)
        }
    }

    private fun getLoanCondition() {
        disposable = newLoanViewModel.getLoanConditions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                maxAmount?.text = it.maxAmount.toString()
                percent?.text = it.percent.toString()
                period?.text = it.period.toString()
                amount?.text = it.maxAmount.toString()
            }, {
                Toast.makeText(
                    context,
                    "Ошибка входа",
                    Toast.LENGTH_LONG
                ).show()
                Log.d("otvet", it.toString())
            })
    }

    private fun getNewLoan(view: View) {
        disposable =
            newLoanViewModel.postLoans(
                amount?.text.toString().toInt(),
                firstName?.text.toString(),
                lastName?.text.toString(),
                percent?.text.toString().toDouble(),
                period?.text.toString().toInt(),
                phone?.text.toString()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.findNavController().navigate(NewLoanFragmentDirections.actionNewLoanFragmentToSuccessfulLoanFragment(it.id, it.amount,it.state))

                    Toast.makeText(
                        context,
                        "Новый кредит",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("otvet", it.toString())
                }, {
                    Toast.makeText(
                        context,
                        "Ошибка",
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