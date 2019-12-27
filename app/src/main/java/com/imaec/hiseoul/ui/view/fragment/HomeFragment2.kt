package com.imaec.hiseoul.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.imaec.hiseoul.R
import com.imaec.hiseoul.databinding.FragmentHomeBinding
import com.imaec.hiseoul.viewmodel.CommonViewModelFactory
import com.imaec.hiseoul.viewmodel.HomeViewModel

class HomeFragment2 : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = setViewModel(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getView(inflater, R.layout.fragment_home, container)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onCreate()
//        viewModel.listImage.observe(this, Observer {
//            viewModel.updateItem(it)
//        })
    }
}