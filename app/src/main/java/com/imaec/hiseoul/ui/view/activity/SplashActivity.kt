package com.imaec.hiseoul.ui.view.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.imaec.hiseoul.R
import com.imaec.hiseoul.databinding.ActivitySplashBinding
import com.imaec.hiseoul.viewmodel.CommonViewModelFactory
import com.imaec.hiseoul.viewmodel.SplashViewModel

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel

    private var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = setViewModel(SplashViewModel::class.java)
        binding = setContent(R.layout.activity_splash)

        viewModel.onCreate()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
//        if (receiver != null) return
//
//        val theFilter = IntentFilter()
//        theFilter.addAction("ACTION_GET_LIST")
//        receiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//                if (intent.action == "ACTION_GET_LIST") {
//                    finish()
//                }
//            }
//        }
//
//        registerReceiver(receiver, theFilter)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()

//        if(receiver != null) {
//            unregisterReceiver(receiver)
//            receiver = null
//        }
    }

    override fun onBackPressed() {
    }

    private fun init() {
    }
}