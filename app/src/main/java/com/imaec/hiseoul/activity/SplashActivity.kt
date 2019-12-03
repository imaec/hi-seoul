package com.imaec.hiseoul.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.imaec.hiseoul.R

class SplashActivity : AppCompatActivity() {

    private var receiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
    }

    override fun onResume() {
        super.onResume()

        if (receiver != null) return

        val theFilter = IntentFilter()
        theFilter.addAction("ACTION_GET_LIST")
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == "ACTION_GET_LIST") {
                    finish()
                }
            }
        }

        registerReceiver(receiver, theFilter)
    }

    override fun onPause() {
        super.onPause()

        if(receiver != null) {
            unregisterReceiver(receiver)
            receiver = null
        }
    }

    override fun onBackPressed() {
    }

    private fun init() {
    }
}