package com.imaec.hiseoul.ui.adapter

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    constructor(callback: (Int) -> Unit): this() {
        this.callback = callback
    }

    protected val TAG = this::class.java.name
    protected lateinit var callback: (Int) -> Unit

    protected fun <T : ViewDataBinding?> getBinding(view: View): T? {
        return DataBindingUtil.bind<T>(view)
    }

    /**
     * Adapter에서 사용 할 ArrayList 추가
     * @param list 어떠한 자료형 또는 DataClass도 받을 수 있는 list
     */
    open fun <T : Any> addItems(list: ArrayList<T>?) {

    }
}