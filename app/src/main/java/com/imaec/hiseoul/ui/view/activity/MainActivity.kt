package com.imaec.hiseoul.ui.view.activity

import android.os.Bundle
import android.view.MenuItem
import com.imaec.hiseoul.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imaec.hiseoul.databinding.ActivityMainBinding
import com.imaec.hiseoul.viewmodel.MainViewModel

class MainActivity : BaseActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = setViewModel(MainViewModel::class.java)
        binding = setContent(R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.onCreate()
        viewModel.initBottomNavigation(binding.frameLayout.id)

        initLayout()
    }

    override fun onBackPressed() {
        viewModel.onBackPressed()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return viewModel.onNavigationItemSelected(item.itemId, binding.frameLayout.id)
    }

    private fun initLayout() {
        binding.bottomMain.itemIconTintList = null
        binding.bottomMain.setOnNavigationItemSelectedListener(this)
    }
}
