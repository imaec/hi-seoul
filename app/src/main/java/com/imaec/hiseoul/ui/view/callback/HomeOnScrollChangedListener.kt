package com.imaec.hiseoul.ui.view.callback

import androidx.core.widget.NestedScrollView

abstract class HomeOnScrollChangedListener : NestedScrollView.OnScrollChangeListener {

    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        if (scrollY < oldScrollY) {
            v?.let {
                if (scrollY < it.measuredHeight/2) {
                    onScrollHalfUp()
                }
            }
        } else {
            v?.let {
                if (scrollY > it.measuredHeight/2) {
                    onScrollHalfDown()
                }
            }
        }
    }

    abstract fun onScrollHalfUp()
    abstract fun onScrollHalfDown()
}