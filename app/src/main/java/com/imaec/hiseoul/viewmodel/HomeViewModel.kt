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
    private val listFilter = arrayOf("P", "R", "O")
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

    var category: MutableLiveData<String> = MutableLiveData("관광지")
    var listImage: MutableLiveData<ArrayList<Item>> = MutableLiveData()
    var listTour: MutableLiveData<ArrayList<Item>> = MutableLiveData()
    var filterIndex = 0
    var categoryIndex = 0

    // 상단 ViewPager Adapter { Item Click }
    var viewPagerAdapter = ViewPagerAdapter {
        listImage.value?.get(it)?.let { item ->
            Log.d("$TAG :::: ", "${item.title}, ${item.contentid}, ${item.contenttypeid}")
        }
    }
    // 둘러보기 Adapter { Item Click }
    var categoryAdapter = CategoryAdapter {
        if (it == categoryIndex) return@CategoryAdapter

        category.value = listCategory[it].category
        categoryIndex = it
        loadImage()
        loadTour(filterIndex)
    }
    // 홈 리스트 Adapter, { Item Click }
    var homeAdapter = HomeAdapter {
        listTour.value?.get(it)?.let { item ->
            Log.d("$TAG :::: ", "${item.title}, ${item.contentid}, ${item.contenttypeid}")
        }
    }

    override fun onCreate() {
        service = HiSeoulService.instance

        loadImage()
        loadTour(0)
        categoryAdapter.addItems(listCategory)
    }

    /**
     * 상단 ViewPager Image 로드
     * @param filterIndex 인기, 최신, 제목 필터 / 기본 값 '최신'으로 변경되지 않음
     */
    @SuppressLint("CheckResult")
    fun loadImage(filterIndex: Int = 1) {
        val callImage = service.callGetList(
            listCategory[categoryIndex].contenttypeid,
            listFilter[filterIndex],
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

    /**
     * 관광지, 문화시설, 행사 등 홈 화면에 보여질 리스트 로드
     * @param filterIndex 인기, 최신, 제목 필터
     */
    fun loadTour(filterIndex: Int) {
        val callTour = service.callGetList(
            listCategory[categoryIndex].contenttypeid,
            listFilter[filterIndex],
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