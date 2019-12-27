package com.imaec.hiseoul.viewmodel.event

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

object MainViewModelBindingAdapters {

    @JvmStatic
    @BindingAdapter("onNavigationItemSelected")
    fun setOnNavigationItemSelected(view: BottomNavigationView, listener: BottomNavigationView.OnNavigationItemSelectedListener) {
        view.setOnNavigationItemSelectedListener(listener)
    }

//    @JvmStatic
//    @BindingAdapter("onNavigationItemSelected")
//    fun setOnNavigationItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.navigation_home -> {
//
//                return true
//            }
//            R.id.navigation_map -> {
//
//                return true
//            }
//            R.id.navigation_my -> {
//
//                return true
//            }
//            R.id.navigation_setting -> {
//
//                return true
//            }
//        }
//        return false
//    }
}