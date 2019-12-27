package com.imaec.hiseoul.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.imaec.hiseoul.model.CategoryData
import com.imaec.hiseoul.model.Item
import com.imaec.hiseoul.retrofit.HiSeoulService
import com.imaec.hiseoul.ui.adapter.ViewPagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(context: Context) : BaseViewModel(context) {

    private lateinit var service: HiSeoulService

    var viewPagerAdapter: ViewPagerAdapter = ViewPagerAdapter()
    var index = 0
    var arrange = "R"
    var listImage: MutableLiveData<ArrayList<Item>> = MutableLiveData()

    private val listCategory = arrayListOf(
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
        viewPagerAdapter = ViewPagerAdapter()

        loadImage()

        
    }

    @SuppressLint("CheckResult")
    private fun loadImage() {
        val callGetList = service.callGetList(
            listCategory[index].contenttypeid,
            arrange,
            8,
            1
        )
        callGetList
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.body.items.listItem }
            .subscribe { listImage.value = it }
            .add()
    }
}