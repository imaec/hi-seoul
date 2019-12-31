package com.imaec.hiseoul.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hiseoul.R
import com.imaec.hiseoul.databinding.ItemImageBinding
import com.imaec.hiseoul.model.Item

class ViewPagerAdapter : BaseAdapter() {

    private val listItem = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position])
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = getBinding<ItemImageBinding>(itemView)

        fun onBind(item: Item) {
            binding?.item = item
        }
    }

    override fun <T : Any> addItems(list: ArrayList<T>?) {
        list?.let {
            listItem.clear()
            it.forEach { item ->
                if (item is Item) listItem.add(item)
            }
        }
    }
}