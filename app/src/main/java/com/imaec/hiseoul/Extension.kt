package com.imaec.hiseoul

import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide

fun FragmentTransaction.hide(fragment1: Fragment, fragment2: Fragment) {
    if (fragment1 != fragment2 && fragment2.isAdded) {
        this.hide(fragment2)
    }
}