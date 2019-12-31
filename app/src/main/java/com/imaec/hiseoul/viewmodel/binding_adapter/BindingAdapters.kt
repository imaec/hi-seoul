package com.imaec.hiseoul.viewmodel.binding_adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.imaec.hiseoul.R
import com.imaec.hiseoul.model.Item
import com.imaec.hiseoul.ui.adapter.BaseAdapter
import com.imaec.hiseoul.ui.adapter.ViewPagerAdapter

object BindingAdapters {

    private val TAG = this::class.java.name

    /**
     * ViewPager2, RecyclerView Adapter 연결
     */
    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(viewGroup: ViewGroup, adapter: ViewPagerAdapter) {
        if (viewGroup is ViewPager2) {
            viewGroup.adapter = adapter
        } else if (viewGroup is RecyclerView) {
            viewGroup.adapter = adapter
        }
    }

    /**
     * ViewPager2, RecyclerView Adapter에 Item 추가
     */
    @JvmStatic
    @BindingAdapter("item")
    fun setItem(viewGroup: ViewGroup, item: MutableLiveData<ArrayList<Item>>) {
        if (viewGroup is ViewPager2) {
            viewGroup.adapter?.let {
                if (it is BaseAdapter) {
                    it.addItems(item.value)
                    it.notifyDataSetChanged()
                }
            }
        } else if (viewGroup is RecyclerView) {
            viewGroup.adapter?.let {
                if (it is BaseAdapter) {
                    it.addItems(item.value)
                    it.notifyDataSetChanged()
                }
            }
        }
    }

    /**
     * url로 ImageView 표시
     */
    @JvmStatic
    @BindingAdapter("imgUrl")
    fun setImageUrl(imageView: ImageView, imgUrl: String?) {
        Glide.with(imageView.context)
            .load(if (imgUrl.isNullOrEmpty()) R.mipmap.ic_launcher else imgUrl)
            .override(360, 200)
            .into(imageView)
    }

    /**
     * TextView Boolean 값으로 Visibility 변경
     */
    @JvmStatic
    @BindingAdapter("isVisible")
    fun setIsVisible(textView: TextView, isVisible: Boolean) {
        textView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}