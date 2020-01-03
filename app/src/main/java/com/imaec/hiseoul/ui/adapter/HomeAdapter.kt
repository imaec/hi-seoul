package com.imaec.hiseoul.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hiseoul.R
import com.imaec.hiseoul.databinding.ItemHomeBinding
import com.imaec.hiseoul.model.Item

class HomeAdapter(callback: (Int) -> Unit) : BaseAdapter(callback) {

    private val listItem = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position], position)
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = getBinding<ItemHomeBinding>(itemView)

        @SuppressLint("SetTextI18n")
        fun onBind(item: Item, position: Int) {
            binding?.item = item
            itemView.setOnClickListener {
                callback(position)
            }
//            itemView.setOnClickListener {
//                context.startActivity(Intent(context, DetailActivity::class.java).apply {
//                    putExtra("title", item.title)
//                    putExtra("contentId", item.contentid)
//                    putExtra("contentTypeId", item.contenttypeid)
//                })
//            }
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

    override fun <T : Any> getItem(position: Int, type: Class<T>): T? {
        return type.cast(listItem[position])
    }
}