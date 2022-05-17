package com.example.cupcake

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cupcake.network.TransactionData

class ClassViewDesign(private val mList: List<TransactionData>) : RecyclerView.Adapter<ClassViewDesign.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val TransactionData = mList[position]

        holder.dateView.text = TransactionData.transactionDate

        // sets the text to the textview from our itemHolder class
        holder.desView.text = TransactionData.description
        holder.fromView.text = TransactionData.receipient?.accountHolder
        holder.fromView.text = TransactionData.sender?.accountHolder
        holder.amountView.text = TransactionData.amount

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dateView: TextView = itemView.findViewById(R.id.dateView)
        val desView: TextView = itemView.findViewById(R.id.desView)
        val fromView: TextView = itemView.findViewById(R.id.fromView)
        val amountView: TextView = itemView.findViewById(R.id.amountView)
    }

}