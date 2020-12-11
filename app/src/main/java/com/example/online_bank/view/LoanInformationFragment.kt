package com.example.online_bank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.online_bank.App
import com.example.online_bank.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoanInformationFragment : Fragment() {
    private val loanInformationViewModel = App.injectLoanInformationViewModel()
    private var disposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_loan_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.let { LoanInformationFragmentArgs.fromBundle(it).id }
        getLoanData(view, id!!)

    }

    private fun getLoanData(view: View, id: Int) {
        disposable = loanInformationViewModel.getLoanData(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                view.findViewById<TextView>(R.id.loanInformationFirstName).text = it.firstName

                view.findViewById<TextView>(R.id.loanInformationLastName).text = it.lastName

                view.findViewById<TextView>(R.id.loanInformationPhoneNumber).text = it.phoneNumber

                view.findViewById<TextView>(R.id.loanInformationID).text = it.id.toString()

                view.findViewById<TextView>(R.id.loanInformationAmount).text = it.amount.toString()

                view.findViewById<TextView>(R.id.loanInformationPercent).text =
                    it.percent.toString()

                view.findViewById<TextView>(R.id.loanInformationPeriod).text = it.period.toString()

                view.findViewById<TextView>(R.id.loanInformationDate).text =
                    it.date.substring(0, it.date.indexOf("T"))

                view.findViewById<TextView>(R.id.loanInformationTime).text =
                    it.date.substring(it.date.indexOf("T") + 1, it.date.indexOf("."))

                view.findViewById<TextView>(R.id.loanInformationState).text = it.state
            }, {
                Toast.makeText(
                    context,
                    "Ошибка загрузки данных",
                    Toast.LENGTH_LONG
                ).show()
            })
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}