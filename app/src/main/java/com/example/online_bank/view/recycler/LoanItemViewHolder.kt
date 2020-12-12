package com.example.online_bank.view.recycler

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.online_bank.R
import com.example.online_bank.repository.data.response.LoansListItem

class LoanItemViewHolder(view: View, private val clickListener: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private var amount: TextView = view.findViewById(R.id.loanAmount)
    private var percent: TextView = view.findViewById(R.id.loanPercent)
    private var period: TextView = view.findViewById(R.id.loanPeriod)
    private var state: TextView = view.findViewById(R.id.loanState)
    private var id: Int = 0

    init {
        view.setOnClickListener {
            clickListener(id)
        }
    }


    fun bind(data: LoansListItem) {
        amount.text = data.amount.toString()
        percent.text = data.percent.toString()
        period.text = data.period.toString()
        state.text = data.state
        id = data.id
    }
}