package com.imaec.hiseoul.ui.view.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.imaec.hiseoul.viewmodel.CommonViewModelFactory

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).onTrimMemory(level)
    }

    protected fun <T : ViewDataBinding?> setContent(resId: Int): T {
        return DataBindingUtil.setContentView<T>(this, resId)
    }

    protected fun <T : ViewModel?> setViewModel(viewModel: Class<T>): T {
        return ViewModelProvider(this, CommonViewModelFactory(this)).get(viewModel)
    }
}