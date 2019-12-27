package com.imaec.hiseoul.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.imaec.hiseoul.CategoryClickCallback
import com.imaec.hiseoul.R
import com.imaec.hiseoul.model.CategoryData
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(var glide: RequestManager, var clickCallback: CategoryClickCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listItem = ArrayList<CategoryData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position], position)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageIcon by lazy { itemView.imageItemCategory }
        private val textCategory by lazy { itemView.textItemCategory }

        fun onBind(item: CategoryData, position: Int) {
            textCategory.text = item.category

            itemView.setOnClickListener {
                clickCallback.onClick(position)
            }
        }
    }

    fun addItem(item: CategoryData) {
        listItem.add(item)
    }
}