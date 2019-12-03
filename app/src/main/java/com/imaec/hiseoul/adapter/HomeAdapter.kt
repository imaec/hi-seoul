package com.imaec.hiseoul.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.imaec.hiseoul.R
import com.imaec.hiseoul.model.Item
import kotlinx.android.synthetic.main.item_home.view.*
import java.text.NumberFormat

class HomeAdapter(var glide: RequestManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listItem = ArrayList<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            holder.onBind(listItem[position])
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageThumb by lazy { itemView.imageItemHome }
        private val textTitle by lazy { itemView.textItemHomeTitle }
        private val textAddress by lazy { itemView.textItemHomeAddress }
        private val textViewCount by lazy { itemView.textItemHomeViewCount }

        @SuppressLint("SetTextI18n")
        fun onBind(item: Item) {
            glide
                .load(item.firstimage)
                .override(240, 200)
                .into(imageThumb)
            textTitle.text = item.title
            if (item.addr1 == null) {
                textAddress.text = "주소정보없음"
            } else {
                textAddress.text = "${item.addr1} ${item.addr2}".replace(" null", "")
            }
            textViewCount.text = NumberFormat.getInstance().format(item.readcount) + " view"

        }
    }

    fun addItem(item: Item) {
        listItem.add(item)
    }

    fun clearItem() {
        listItem.clear()
    }
}