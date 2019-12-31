package com.imaec.hiseoul.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hiseoul.R
import com.imaec.hiseoul.databinding.ItemCategoryBinding
import com.imaec.hiseoul.model.CategoryData
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(var callback: (Int) -> Unit) : BaseAdapter() {

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

        private val binding = getBinding<ItemCategoryBinding>(itemView)

        fun onBind(item: CategoryData, position: Int) {
            binding?.item = item

            itemView.setOnClickListener {
                callback(position)
            }
        }
    }

    override fun <T : Any> addItems(list: ArrayList<T>?) {
        list?.let {
            listItem.clear()
            list.forEach { item ->
                if (item is CategoryData) listItem.add(item)
            }
            notifyDataSetChanged()
        }
    }
}