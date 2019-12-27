package com.imaec.hiseoul.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hiseoul.R

class DetailAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listItem = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position])
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(item: String) {

        }
    }

    fun addItem(item: String) {
        listItem.add(item)
    }
}