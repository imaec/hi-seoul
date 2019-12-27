package com.imaec.hiseoul.ui.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.imaec.hiseoul.viewmodel.CommonViewModelFactory

abstract class BaseFragment : Fragment() {

    protected fun <T : ViewDataBinding?> getView(inflater: LayoutInflater, resId: Int, container: ViewGroup?): T {
        return DataBindingUtil.inflate<T>(inflater, resId, container, false)
    }

    protected fun <T : ViewModel?> setViewModel(viewModel: Class<T>): T {
        return ViewModelProvider(this, CommonViewModelFactory(context!!)).get(viewModel)
    }
}