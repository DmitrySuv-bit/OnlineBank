package com.example.online_bank.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.online_bank.R

class SuccessfulLoanFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_successful_loan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.successfulLoanID).text =
            arguments?.let { SuccessfulLoanFragmentArgs.fromBundle(it).id }.toString()

        view.findViewById<TextView>(R.id.successfulLoanAmount).text =
            arguments?.let { SuccessfulLoanFragmentArgs.fromBundle(it).amount }.toString()

        view.findViewById<TextView>(R.id.successfulLoanState).text =
            arguments?.let { SuccessfulLoanFragmentArgs.fromBundle(it).state }.toString()

        view.findViewById<Button>(R.id.successfulLoanButton).setOnClickListener {
            view.findNavController().navigate(R.id.action_successfulLoanFragment_to_homeFragment)
        }
    }
}