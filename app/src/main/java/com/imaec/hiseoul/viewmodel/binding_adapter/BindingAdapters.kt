package com.imaec.hiseoul.viewmodel.binding_adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.imaec.hiseoul.R
import com.imaec.hiseoul.model.Item
import com.imaec.hiseoul.ui.adapter.ViewPagerAdapter

object BindingAdapters {

    private val TAG = this::class.java.name

    /**
     * ViewPager2 Adapter 연결
     */
    @JvmStatic
    @BindingAdapter("adapter")
    fun setAdapter(viewPager: ViewPager2, adapter: ViewPagerAdapter) {
        viewPager.adapter = adapter
    }

    /**
     * ViewPager2 Item Adapter에 추가
     */
    @JvmStatic
    @BindingAdapter("item")
    fun setItem(viewPager: ViewPager2, item: MutableLiveData<ArrayList<Item>>) {
        viewPager.adapter?.let {
            if (it is ViewPagerAdapter) {
                it.addItems(item.value)
                it.notifyDataSetChanged()
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