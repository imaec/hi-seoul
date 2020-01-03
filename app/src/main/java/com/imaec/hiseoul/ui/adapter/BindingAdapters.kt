package com.imaec.hiseoul.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.imaec.hiseoul.R
import com.imaec.hiseoul.model.Item
import java.text.NumberFormat

@SuppressLint("SetTextI18n")
object BindingAdapters {

    private val TAG = this::class.java.name

    /**
     * ViewPager2, RecyclerView Adapter 연결
     * @param viewGroup ViewPager2 또는 RecyclerView
     * @param adapter ViewPager2 또는 RecyclerView에 연결 될 Adapter
     */
    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(viewGroup: ViewGroup, adapter: BaseAdapter) {
        if (viewGroup is ViewPager2) {
            viewGroup.adapter = adapter
        } else if (viewGroup is RecyclerView) {
            viewGroup.adapter = adapter
        }
    }

    /**
     * ViewPager2, RecyclerView Adapter에 Item 추가
     * @param viewGroup ViewPager2 또는 RecyclerView
     * @param item ViewPager2 또는 RecyclerView의 Adapter에 들어갈 List
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
     * View Boolean 값으로 Visibility 변경
     */
    @JvmStatic
    @BindingAdapter("isVisible")
    fun setIsVisible(view: View, isVisible: Boolean) {
        view.visibility = if (isVisible) View.VISIBLE else View.GONE
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
     * TextView Address 표시
     */
    @JvmStatic
    @BindingAdapter(value = ["app:addr1", "app:addr2"], requireAll = false)
    fun setAddress(textView: TextView, addr1: String?, addr2: String?) {
        textView.text = if (addr1 == null) {
            "주소정보없음"
        } else {
            if (addr2 == null) {
                addr1
            } else {
                "$addr1 $addr2"
            }
        }
    }

    /**
     * TextView ViewCount 표시
     */
    @JvmStatic
    @BindingAdapter("viewCount")
    fun setViewCount(textView: TextView, viewCount: Int) {
        textView.text = "${NumberFormat.getInstance().format(viewCount)} view"
    }
}