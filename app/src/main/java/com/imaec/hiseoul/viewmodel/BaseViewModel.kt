package com.imaec.hiseoul.viewmodel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(var context: Context) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    protected val TAG = this::class.java.name

    /**
     * Activity LifeCycle에서 호출 될 메소드
     */
    abstract fun onCreate()
    open fun onResume() {}
    open fun onPause() {}
    open fun onDestroy() {}
    open fun onBackPressed() {}
    open fun onLowMemory() {}
    open fun onTrimMemory() {}

    open fun startActivity(intent: Intent) {
        context.startActivity(intent)
    }

    open fun startActivityForResult(intent: Intent, resultCode: Int) {
        (context as AppCompatActivity).startActivityForResult(intent, resultCode)
    }

    open fun finish() {
        (context as AppCompatActivity).finish()
    }

    fun Disposable.add() {
        compositeDisposable.add(this)
    }

    /**
     * ViewModel이 LifeCycle에서 종료되었을 때 호출되어 불필요한 메모리 초기화
     */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}