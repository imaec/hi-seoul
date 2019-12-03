package com.imaec.hiseoul

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HomeItemDecoration(var context: Context) : RecyclerView.ItemDecoration() {

    private val offset1 = dp(context, 6)
    private val offset2 = dp(context, 12)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

        outRect.top = offset1

        if (parent.getChildLayoutPosition(view) == parent.adapter!!.itemCount - 1 ||
            parent.getChildLayoutPosition(view) == parent.adapter!!.itemCount - 2) {
            outRect.bottom = offset2
        } else {
            outRect.bottom = offset1
        }
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            outRect.left = offset2
            outRect.right = offset1
        } else {
            outRect.left = offset1
            outRect.right = offset2
        }
    }

    fun dp(context: Context, dpValue: Int): Int {
        val d = context.resources.displayMetrics.density
        return (dpValue * d).toInt()
    }
}