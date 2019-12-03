package com.imaec.hiseoul.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.imaec.hiseoul.CategoryClickCallback
import com.imaec.hiseoul.HomeItemDecoration
import com.imaec.hiseoul.ParallaxPageTransformer
import com.imaec.hiseoul.ParallaxPageTransformer.ParallaxTransformInformation.Companion.PARALLAX_EFFECT_DEFAULT
import com.imaec.hiseoul.R
import com.imaec.hiseoul.adapter.CategoryAdapter
import com.imaec.hiseoul.adapter.FragmentAdapter
import com.imaec.hiseoul.adapter.HomeAdapter
import com.imaec.hiseoul.model.AreaData
import com.imaec.hiseoul.model.CategoryData
import com.imaec.hiseoul.model.Item
import com.imaec.hiseoul.retrofit.HiSeoulService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.content_alpha_bg.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryLayoutManger: LinearLayoutManager
    private lateinit var adapter: HomeAdapter
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var itemDecoration: HomeItemDecoration
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var pageTransformer: ParallaxPageTransformer

    private var completedCount = 0
    private var categoryIndex = 0
    private var filterIndex = 0
    private var filterTempIndex = 0

    private val clickCallback = object : CategoryClickCallback {
        override fun onClick(position: Int) {
            if (categoryIndex == position) return

            categoryIndex = position
            adapter.clearItem()
            listAll[position].forEach {
                adapter.addItem(it)
            }
            adapter.notifyDataSetChanged()
            textHomeCategory.text = listCategory[position].category
        }
    }

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
    private val listAll = arrayListOf<ArrayList<Item>>(ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList(), ArrayList())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        viewPagerHome.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                indicatorNew.selectDot(position)
            }

        })

        textHomeFilter.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        viewAlphaBg.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            filterTempIndex = filterIndex

            when(filterIndex) {
                0 -> setTextColor(textBottomSheetPopular, textBottomSheetNew, textBottomSheetName)
                1 -> setTextColor(textBottomSheetNew, textBottomSheetName, textBottomSheetPopular)
                2 -> setTextColor(textBottomSheetName, textBottomSheetPopular, textBottomSheetNew)
            }
        }

        textBottomSheetCancel.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            filterTempIndex = filterIndex

            when(filterIndex) {
                0 -> setTextColor(textBottomSheetPopular, textBottomSheetNew, textBottomSheetName)
                1 -> setTextColor(textBottomSheetNew, textBottomSheetName, textBottomSheetPopular)
                2 -> setTextColor(textBottomSheetName, textBottomSheetPopular, textBottomSheetNew)
            }
        }

        textBottomSheetConfirm.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            filterIndex = filterTempIndex

            adapter.clearItem()
            getList(filterIndex, false)
        }

        textBottomSheetPopular.setOnClickListener {
            filterTempIndex = 0
            setTextColor(textBottomSheetPopular, textBottomSheetNew, textBottomSheetName)
        }

        textBottomSheetNew.setOnClickListener {
            filterTempIndex = 1
            setTextColor(textBottomSheetNew, textBottomSheetName, textBottomSheetPopular)
        }

        textBottomSheetName.setOnClickListener {
            filterTempIndex = 2
            setTextColor(textBottomSheetName, textBottomSheetPopular, textBottomSheetNew)
        }

        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if ((viewAlphaBg.visibility == View.GONE && newState == BottomSheetBehavior.STATE_SETTLING)
                    || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    viewAlphaBg.visibility = View.VISIBLE
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    viewAlphaBg.visibility = View.GONE
                }
            }
        })

//        scrollHome.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
//            if (scrollY < 1000) {
//                (context as AppCompatActivity).window.setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS)
//            } else {
//                (context as AppCompatActivity).window.clearFlags(FLAG_LAYOUT_NO_LIMITS)
//            }
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun init() {
        compositeDisposable = CompositeDisposable()

        fragmentAdapter = FragmentAdapter(childFragmentManager)
        viewPagerHome.adapter = fragmentAdapter

        categoryAdapter = CategoryAdapter(Glide.with(this), clickCallback)
        categoryLayoutManger = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        recyclerHomeCategory.adapter = categoryAdapter
        recyclerHomeCategory.layoutManager = categoryLayoutManger
        setCategory()

        adapter = HomeAdapter(Glide.with(this))
        layoutManager = GridLayoutManager(context, 2)
        itemDecoration = HomeItemDecoration(context as AppCompatActivity)
        recyclerHome.adapter = adapter
        recyclerHome.layoutManager = layoutManager
        recyclerHome.addItemDecoration(itemDecoration)
        getList(0, true)

        bottomSheetBehavior = BottomSheetBehavior.from(linearBottomSheetHome)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        pageTransformer = ParallaxPageTransformer()
                .addViewToParallax(ParallaxPageTransformer.ParallaxTransformInformation(R.id.imageNew, 2f, 2f))
        viewPagerHome.setPageTransformer(true, pageTransformer)

        (context as AppCompatActivity).window.setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS)
    }

    private fun setIndicator() {
        //원사이의 간격
        indicatorNew.setItemMargin(15)
        //애니메이션 속도
        indicatorNew.setAnimDuration(300)
        //indecator 생성
        indicatorNew.createDotPanel(
            fragmentAdapter.count,
            R.drawable.indicator_gray,
            R.drawable.indicator_white
        )
    }

    private fun setCategory() {
        for (category in listCategory) {
            categoryAdapter.addItem(category)
        }
        categoryAdapter.notifyDataSetChanged()
    }

    @SuppressLint("CheckResult")
    private fun getList(filterIndex: Int, isInit: Boolean) {
        val listFilter = arrayOf("P", "R", "O")
        val service = HiSeoulService.instance
        val listObservable = ArrayList<Observable<AreaData>>()
        listCategory.forEach {
            listObservable.add(service.callGetList(it.contenttypeid, listFilter[filterIndex], 60, 1))
        }
        listAll.forEach {
            it.clear()
        }
        Observable
            .merge(listObservable)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapIterable {
                it.body.items.listItem
            }
            .subscribe({
                for ((i, category) in listCategory.withIndex()) {
                    if (it.contenttypeid == category.contenttypeid) {
                        listAll[i].add(it)
                    }
                }
                if (it.contenttypeid == listCategory[categoryIndex].contenttypeid) adapter.addItem(it)
            }, {
                Log.d("it :::: ", it.message)
            }, {
                adapter.notifyDataSetChanged()

                if (completedCount < 2) completedCount++
                if (completedCount == 2) (context as AppCompatActivity).sendBroadcast(Intent("ACTION_GET_LIST"))
            }, {
                compositeDisposable.add(it)
            })


        if (!isInit) return

        val listObservable2 = ArrayList<Observable<AreaData>>()
        listCategory.forEach {
            listObservable2.add(service.callGetList(it.contenttypeid, "R", 1, 1))
        }
        Observable
            .merge(listObservable2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapIterable {
                it.body.items.listItem
            }
            .subscribe({
                val fragment = NewFragment().apply {
                    arguments = Bundle().apply {
                        putString("img", it.firstimage)
                        putString("title", it.title)
                    }
                }
                fragmentAdapter.addItem(fragment)
            }, {
                Log.d("it :::: ", it.message)
            }, {
                fragmentAdapter.notifyDataSetChanged()
                setIndicator()
                completedCount++
                if (completedCount == 2) (context as AppCompatActivity).sendBroadcast(Intent("ACTION_GET_LIST"))
            }, {
                compositeDisposable.add(it)
            })
    }

    private fun setTextColor(textView1: TextView, textView2: TextView, textView3: TextView) {
        textView1.setTextColor(context!!.resources.getColor(R.color.colorPrimaryDark))
        textView2.setTextColor(Color.parseColor("#999999"))
        textView3.setTextColor(Color.parseColor("#999999"))

        textView1.setBackgroundResource(R.drawable.bg_filter_on)
        textView2.setBackgroundResource(R.drawable.bg_filter_off)
        textView3.setBackgroundResource(R.drawable.bg_filter_off)
    }

    fun setScrollUp() {
        if (scrollHome != null) scrollHome.smoothScrollTo(0, 0)
    }
}