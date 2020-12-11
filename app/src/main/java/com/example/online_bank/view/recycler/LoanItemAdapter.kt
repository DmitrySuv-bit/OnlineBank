package com.example.online_bank.view.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.online_bank.R
import com.example.online_bank.repository.data.response.LoansListItem

class LoanItemAdapter(private val clickListener: (Int) -> Unit) : RecyclerView.Adapter<LoanItemViewHolder>() {
    private val itemsList = mutableListOf<LoansListItem>()

    fun updateItem(newItem: List<LoansListItem>) {
        itemsList.clear()
        itemsList.addAll(newItem)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.loan_item, parent, false)

        return LoanItemViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: LoanItemViewHolder, position: Int) {
        val item = itemsList[position]

        holder.bind(item)
    }

    override fun getItemCount(): Int = itemsList.size
}