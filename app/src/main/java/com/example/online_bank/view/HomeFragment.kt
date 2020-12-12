package com.example.online_bank.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.online_bank.App
import com.example.online_bank.R
import com.example.online_bank.repository.data.response.LoansListItem
import com.example.online_bank.view.recycler.LoanItemAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment() {
    private val homeViewModel = App.injectHomeViewModel()
    private var disposable: Disposable? = null

    private val approvedButton: TextView?
        get() = view?.findViewById(R.id.approvedButton)

    private val registeredButton: TextView?
        get() = view?.findViewById(R.id.registeredButton)

    private val rejectedButton: TextView?
        get() = view?.findViewById(R.id.rejectedButton)

    private var rejectedLoanList = mutableListOf<LoansListItem>()
    private var registeredLoanList = mutableListOf<LoansListItem>()
    private var approvedLoanList = mutableListOf<LoansListItem>()

    private val itemAdapter = LoanItemAdapter {
        view?.findNavController()?.navigate(
            HomeFragmentDirections.actionHomeFragmentToLoanInformationFragment(it)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.loansRecyclerView).apply {
            adapter = itemAdapter
        }

        view.findViewById<FloatingActionButton>(R.id.addLoanButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_newLoanFragment)
        }

        view.findViewById<FloatingActionButton>(R.id.exitButton).setOnClickListener {
            homeViewModel.logOut()
            view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }

        approvedButton?.setOnClickListener {
            if (approvedLoanList.isNotEmpty()) {
                setActiveButton(approvedButton)
                setNoActiveButton(registeredButton)
                setNoActiveButton(rejectedButton)

                itemAdapter.updateItem(approvedLoanList)
            } else {
                getToast("Пусто")
            }
        }

        registeredButton?.setOnClickListener {
            if (registeredLoanList.isNotEmpty()) {
                setNoActiveButton(approvedButton)
                setActiveButton(registeredButton)
                setNoActiveButton(rejectedButton)

                itemAdapter.updateItem(registeredLoanList)
            } else {
                getToast("Пусто")
            }
        }

        rejectedButton?.setOnClickListener {
            if (rejectedLoanList.isNotEmpty()) {
                setNoActiveButton(approvedButton)
                setNoActiveButton(registeredButton)
                setActiveButton(rejectedButton)

                itemAdapter.updateItem(rejectedLoanList)
            } else {
                getToast("Пусто")
            }
        }

        if (homeViewModel.isAuthorization()) {
            showListLoans()
        } else {
            view.findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
        }
    }

    private fun showListLoans() {
        disposable = homeViewModel.getLoansList()
            .map {
                if (it.isNotEmpty()) {
                    rejectedLoanList = it.filter { item ->
                        item.state == "REJECTED"
                    }.sortedByDescending { sorted ->
                        sorted.amount
                    } as MutableList<LoansListItem>

                    registeredLoanList = it.filter { item ->
                        item.state == "REGISTERED"
                    }.sortedByDescending { sorted ->
                        sorted.amount
                    } as MutableList<LoansListItem>

                    it.filter { item ->
                        item.state == "APPROVED"
                    }.sortedByDescending { sorted ->
                        sorted.amount
                    }
                } else {
                    it
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                approvedLoanList.addAll(it)

                itemAdapter.updateItem(it)

                if (it.isNotEmpty()) {
                    setActiveButton(approvedButton)
                }
            }, {
                getToast("Ошибка авторизации")
            })
    }

    private fun setActiveButton(button: TextView?) {
        button?.setBackgroundColor(resources.getColor(R.color.active_button, null))
    }

    private fun setNoActiveButton(button: TextView?) {
        button?.setBackgroundColor(resources.getColor(R.color.no_active_button, null))
    }

    private fun getToast(text: String) {
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}