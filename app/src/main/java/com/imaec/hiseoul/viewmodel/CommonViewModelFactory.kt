package com.imaec.hiseoul.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class CommonViewModelFactory(var context: Context) : ViewModelProvider.Factory {

    /**
     * ViewModel을 생성
     */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(context) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(context) as T
            }
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(context) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}