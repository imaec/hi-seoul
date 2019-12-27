package com.imaec.hiseoul.ui.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.imaec.hiseoul.R
import com.imaec.hiseoul.ui.adapter.DetailAdapter
import com.imaec.hiseoul.ui.adapter.FragmentAdapter
import com.imaec.hiseoul.ui.view.fragment.DetailFragment
import com.imaec.hiseoul.ui.view.fragment.ImageFragment
import com.imaec.hiseoul.ui.view.fragment.IntroFragment
import com.imaec.hiseoul.retrofit.HiSeoulService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var compositeDisposable: CompositeDisposable
    private lateinit var fragmentImageAdapter: FragmentAdapter
    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var adapter: DetailAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var contentId = 0
    private var contentTypeId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        init()

        appbarDetail.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBar, verticalOffset ->
            val offsetAlpha = (appBar.y / appbarDetail.totalScrollRange)
            viewPagerDetailImage.alpha = 1 - (offsetAlpha * -1)
        })

        viewPagerDetailImage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                indicatorDetail.selectDot(position)
            }
        })

        viewPagerDetail.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabDetail))
        tabDetail.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                viewPagerDetail.currentItem = p0.position
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun init() {
        compositeDisposable = CompositeDisposable()

        collapseToolbarDetail.title = intent.getStringExtra("title")
        tabDetail.addTab(tabDetail.newTab().setText("소개"))
        tabDetail.addTab(tabDetail.newTab().setText("상세정보"))

        contentId = intent.getIntExtra("contentId", 0)
        contentTypeId = intent.getIntExtra("contentTypeId", 0)

        fragmentImageAdapter =
            FragmentAdapter(supportFragmentManager)
        fragmentAdapter = FragmentAdapter(supportFragmentManager)
        viewPagerDetailImage.adapter = fragmentImageAdapter
        viewPagerDetail.adapter = fragmentAdapter
        setTab()

        adapter = DetailAdapter()
        layoutManager = LinearLayoutManager(this)
        recyclerDetail.adapter = adapter
        recyclerDetail.layoutManager = layoutManager
        recyclerDetail.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
        getDetail()
    }

    private fun setTab() {
        fragmentAdapter.addItem(IntroFragment().apply {
            arguments = Bundle().apply {
                putInt("contentId", contentId)
                putInt("contentTypeId", contentTypeId)
            }
        })
        fragmentAdapter.addItem(DetailFragment())
        fragmentAdapter.notifyDataSetChanged()
    }

    @SuppressLint("CheckResult")
    private fun getDetail(imageYN: String = "Y") {
        val service = HiSeoulService.instance
        val callGetImage = service.callGetImage(contentId, contentTypeId, imageYN)
        callGetImage
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMapIterable {
                it.body.items.listItem
            }
            .subscribe({
                val fragment = ImageFragment().apply {
                    arguments = Bundle().apply {
                        putString("img", it.originimgurl)
                        putString("title", "")
                    }
                }
                fragmentImageAdapter.addItem(fragment)
            }, {
                Log.d("it2 :::: ", it.message)
            }, {
                fragmentImageAdapter.notifyDataSetChanged()
                setIndicator()
            }, {
                compositeDisposable.add(it)
            })
    }

    private fun setIndicator() {
        //원사이의 간격
        indicatorDetail.setItemMargin(15)
        //애니메이션 속도
        indicatorDetail.setAnimDuration(300)
        //indecator 생성
        indicatorDetail.createDotPanel(
            fragmentImageAdapter.count,
            R.drawable.indicator_gray,
            R.drawable.indicator_white
        )
    }
}