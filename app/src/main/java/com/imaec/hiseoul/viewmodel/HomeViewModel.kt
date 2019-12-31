package com.imaec.hiseoul.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.imaec.hiseoul.ACTION_GET_LIST
import com.imaec.hiseoul.model.CategoryData
import com.imaec.hiseoul.model.Item
import com.imaec.hiseoul.retrofit.HiSeoulService
import com.imaec.hiseoul.ui.adapter.CategoryAdapter
import com.imaec.hiseoul.ui.adapter.HomeAdapter
import com.imaec.hiseoul.ui.adapter.ViewPagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(context: Context) : BaseViewModel(context) {

    private lateinit var service: HiSeoulService
    private var completedCount = 0
    private var onClickCategory: (Int) -> Unit = {
        Log.d("$TAG :::: ", listCategory[it].category)
    }

    var viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter()
    var homeAdapter: HomeAdapter = HomeAdapter()
    var categoryAdapter: CategoryAdapter = CategoryAdapter(onClickCategory)
    var listImage: MutableLiveData<ArrayList<Item>> = MutableLiveData()
    var listTour: MutableLiveData<ArrayList<Item>> = MutableLiveData()
    var index = 0
    var arrange = "P"
    val listCategory = arrayListOf(
        CategoryData("관광지", 12),
        CategoryData("문화시설", 14),
        CategoryData("행사", 15),
        CategoryData("여행코스", 25),
        CategoryData("레포츠", 28),
        CategoryData("숙박", 32),
        CategoryData("쇼핑", 38),
        CategoryData("음식점", 39)
    )

    override fun onCreate() {
        service = HiSeoulService.instance

        loadImage()
        loadTour()
        categoryAdapter.addItems(listCategory)
    }

    @SuppressLint("CheckResult")
    private fun loadImage() {
        val callImage = service.callGetList(
            listCategory[index].contenttypeid,
            "R",
            8,
            1
        )
        callImage
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.body.items.listItem
            }
            .subscribe {
                listImage.value = it
                if (completedCount < 2) completedCount++
                if (completedCount == 2) (context as AppCompatActivity).sendBroadcast(Intent(ACTION_GET_LIST))
            }
            .add()
    }

    private fun loadTour() {
        val callTour = service.callGetList(
            listCategory[index].contenttypeid,
            arrange,
            60,
            1
        )
        callTour
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.body.items.listItem
            }
            .subscribe {
                listTour.value = it
                if (completedCount < 2) completedCount++
                if (completedCount == 2) (context as AppCompatActivity).sendBroadcast(Intent(ACTION_GET_LIST))
            }
            .add()
    }
}