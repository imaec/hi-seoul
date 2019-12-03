package com.imaec.hiseoul.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.imaec.hiseoul.R
import com.imaec.hiseoul.fragment.HomeFragment
import com.imaec.hiseoul.fragment.MapFragment
import com.imaec.hiseoul.fragment.MyFragment
import com.imaec.hiseoul.fragment.SettingFragment
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.content.IntentFilter
import android.util.Log
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    private lateinit var transaction: FragmentTransaction
    private lateinit var fragmentHome: HomeFragment
    private lateinit var fragmentMap: MapFragment
    private lateinit var fragmentMy: MyFragment
    private lateinit var fragmentSetting: SettingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, SplashActivity::class.java))

        init()

        bottomMain.setOnNavigationItemSelectedListener {
            transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.navigationHome -> {
                    fragmentHome.setScrollUp()
                    replaceFragment(fragmentHome)
                    // setBottomIcon(it.itemId)
                }
                R.id.navigationMap -> {
                    replaceFragment(fragmentMap)
                    // setBottomIcon(it.itemId)
                }
                R.id.navigationMy -> {
                    replaceFragment(fragmentMy)
                    // setBottomIcon(it.itemId)
                }
                R.id.navigationSetting -> {
                    replaceFragment(fragmentSetting)
                    // setBottomIcon(it.itemId)
                }
            }
            true
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Glide.get(this).onTrimMemory(level)
    }

    private fun init() {
        // bottomMain.itemIconTintList = null
        transaction = supportFragmentManager.beginTransaction()
        fragmentHome = HomeFragment()
        fragmentMap = MapFragment()
        fragmentMy = MyFragment()
        fragmentSetting = SettingFragment()
        // backPressHandler = BackPressHandler(this)

        replaceFragment(fragmentHome)
        // transaction.replace(frameLayout.id, fragmentHome).commit()
        // setBottomIcon(R.id.navigation_notification)
    }

    private fun replaceFragment(fragment: Fragment) {
        transaction = supportFragmentManager.beginTransaction()
        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.add(frameLayout.id, fragment)
        }

        supportFragmentManager.fragments.forEach {
            if (it != fragment && it.isAdded) {
                transaction.hide(it)
            }
        }
        transaction.commit()
    }
}
