package com.imaec.hiseoul.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.imaec.hiseoul.R
import com.imaec.hiseoul.hide
import com.imaec.hiseoul.ui.view.activity.MainActivity
import com.imaec.hiseoul.ui.view.fragment.HomeFragment2
import com.imaec.hiseoul.ui.view.fragment.MapFragment
import com.imaec.hiseoul.ui.view.fragment.MyFragment
import com.imaec.hiseoul.ui.view.fragment.SettingFragment

class MainViewModel(context: Context) : BaseViewModel(context) {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentHome: HomeFragment2
    private lateinit var fragmentMap: MapFragment
    private lateinit var fragmentMy: MyFragment
    private lateinit var fragmentSetting: SettingFragment

    override fun onCreate() {
        // startActivity(Intent(context, SplashActivity::class.java))

        fragmentManager = (context as MainActivity).supportFragmentManager
        fragmentHome = HomeFragment2()
        fragmentMap = MapFragment()
        fragmentMy = MyFragment()
        fragmentSetting = SettingFragment()
    }

    fun initBottomNavigation(containerId: Int) {
        replaceFragment(fragmentHome, containerId)
    }

    fun onNavigationItemSelected(itemId: Int, containerId: Int): Boolean {
        return when (itemId) {
            R.id.navigation_home -> {
                replaceFragment(fragmentHome, containerId)
            }
            R.id.navigation_map -> {
                replaceFragment(fragmentMap, containerId)
            }
            R.id.navigation_my -> {
                replaceFragment(fragmentMy, containerId)
            }
            R.id.navigation_setting -> {
                replaceFragment(fragmentSetting, containerId)
            }
            else -> false
        }
    }

    private fun replaceFragment(fragment: Fragment, containerId: Int): Boolean {
        fragmentManager.commit {
            if (fragment.isAdded) {
                this.show(fragment)
            } else {
                this.add(containerId, fragment)
            }

            fragmentManager.fragments.forEach {
                this.hide(fragment, it)
            }
        }

        return true
    }
}