package com.example.online_bank.view

import android.graphics.Color
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
    private val itemAdapter = LoanItemAdapter {
        view?.findNavController()?.navigate(
            HomeFragmentDirections.actionHomeFragmentToLoanInformationFragment(it)
        )
    }
    private var rejectedLoanList = mutableListOf<LoansListItem>()
    private var registeredLoanList = mutableListOf<LoansListItem>()
    private var approvedLoanList = mutableListOf<LoansListItem>()

    private val approvedButton: TextView?
        get() = view?.findViewById(R.id.approvedButton)

    private val registeredButton: TextView?
        get() = view?.findViewById(R.id.registeredButton)

    private val rejectedButton: TextView?
        get() = view?.findViewById(R.id.rejectedButton)

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
                approvedButton?.setBackgroundColor(resources.getColor(R.color.active_button, null))
                registeredButton?.setBackgroundColor(resources.getColor(R.color.no_active_button, null))
                rejectedButton?.setBackgroundColor(resources.getColor(R.color.no_active_button, null))

                itemAdapter.updateItem(approvedLoanList)
            } else {
                Toast.makeText(
                    context,
                    "Пусто",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        registeredButton?.setOnClickListener {
            if (registeredLoanList.isNotEmpty()) {
                approvedButton?.setBackgroundColor(resources.getColor(R.color.no_active_button,null))
                registeredButton?.setBackgroundColor(resources.getColor(R.color.active_button,null))
                rejectedButton?.setBackgroundColor(resources.getColor(R.color.no_active_button,null))

                itemAdapter.updateItem(registeredLoanList)
            } else {
                Toast.makeText(
                    context,
                    "Пусто",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        rejectedButton?.setOnClickListener {
            if (rejectedLoanList.isNotEmpty()) {
                approvedButton?.setBackgroundColor(resources.getColor(R.color.no_active_button,null))
                registeredButton?.setBackgroundColor(resources.getColor(R.color.no_active_button,null))
                rejectedButton?.setBackgroundColor(resources.getColor(R.color.active_button,null))

                itemAdapter.updateItem(rejectedLoanList)
            } else {
                Toast.makeText(
                    context,
                    "Пусто",
                    Toast.LENGTH_LONG
                ).show()
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
                rejectedLoanList = it.filter { item ->
                    item.state == "REJECTED"
                }.sortedByDescending {sorted ->
                    sorted.amount
                } as MutableList<LoansListItem>

                registeredLoanList = it.filter { item ->
                    item.state == "REGISTERED"
                }.sortedByDescending {sorted ->
                    sorted.amount
                } as MutableList<LoansListItem>

                it.filter { item ->
                    item.state == "APPROVED"
                }.sortedByDescending {sorted ->
                    sorted.amount
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                approvedLoanList.addAll(it)

                itemAdapter.updateItem(it)

                if (it.isNotEmpty()) {
                    approvedButton?.setBackgroundColor(resources.getColor(R.color.active_button,null))
                }
            }, {
            })
    }

    override fun onDestroy() {
        disposable?.dispose()
        disposable = null
        super.onDestroy()
    }
}