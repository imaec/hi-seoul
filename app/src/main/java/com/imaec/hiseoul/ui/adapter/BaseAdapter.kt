package com.imaec.hiseoul.ui.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.imaec.hiseoul.model.Item

abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected val TAG = this::class.java.name

    protected fun <T : ViewDataBinding?> getBinding(view: View): T? {
        return DataBindingUtil.bind<T>(view)
    }

    open fun <T : Any> addItems(list: ArrayList<T>?) {

    }
}