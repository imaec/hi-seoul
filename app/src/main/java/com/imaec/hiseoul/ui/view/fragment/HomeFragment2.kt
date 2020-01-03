package com.imaec.hiseoul.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.imaec.hiseoul.R
import com.imaec.hiseoul.databinding.FragmentHomeBinding
import com.imaec.hiseoul.ui.view.callback.HomeOnScrollChangedListener
import com.imaec.hiseoul.util.HomeItemDecoration
import com.imaec.hiseoul.util.ParallaxPageTransformer
import com.imaec.hiseoul.viewmodel.HomeViewModel

class HomeFragment2 : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private var filterTempIndex = 0
    var isBottomSheetHidden = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = setViewModel(HomeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getView(inflater, R.layout.fragment_home, container)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLayout()
        initListener()

        viewModel.onCreate()
        viewModel.listImage.observe(this, Observer {
            binding.viewPager.currentItem = 0
            setIndicator(it.size)
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            // 필터 버튼
            R.id.text_filter -> bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            // 필터가 올라와 있을 때 배경
            R.id.view_alpha_bg -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

                filterTempIndex = viewModel.filterIndex
                when(viewModel.filterIndex) {
                    0 -> setTextColor(binding.textBottomSheetPopular, binding.textBottomSheetNew, binding.textBottomSheetName)
                    1 -> setTextColor(binding.textBottomSheetNew, binding.textBottomSheetName, binding.textBottomSheetPopular)
                    2 -> setTextColor(binding.textBottomSheetName, binding.textBottomSheetPopular, binding.textBottomSheetNew)
                }
            }
            // 필터 취소
            R.id.text_bottom_sheet_cancel -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

                filterTempIndex = viewModel.filterIndex
                when(viewModel.filterIndex) {
                    0 -> setTextColor(binding.textBottomSheetPopular, binding.textBottomSheetNew, binding.textBottomSheetName)
                    1 -> setTextColor(binding.textBottomSheetNew, binding.textBottomSheetName, binding.textBottomSheetPopular)
                    2 -> setTextColor(binding.textBottomSheetName, binding.textBottomSheetPopular, binding.textBottomSheetNew)
                }
            }
            // 필터 확인
            R.id.text_bottom_sheet_confirm -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

                if (viewModel.filterIndex == filterTempIndex) return

                viewModel.filterIndex = filterTempIndex
                viewModel.loadTour(viewModel.filterIndex)
            }
            // 필터 인기
            R.id.text_bottom_sheet_popular -> {
                filterTempIndex = 0
                setTextColor(binding.textBottomSheetPopular, binding.textBottomSheetNew, binding.textBottomSheetName)
            }
            // 필터 최신
            R.id.text_bottom_sheet_new -> {
                filterTempIndex = 1
                setTextColor(binding.textBottomSheetNew, binding.textBottomSheetName, binding.textBottomSheetPopular)
            }
            // 필터 제목
            R.id.text_bottom_sheet_name -> {
                filterTempIndex = 2
                setTextColor(binding.textBottomSheetName, binding.textBottomSheetPopular, binding.textBottomSheetNew)
            }
            // ScrollView 위로 올리기
            R.id.fab -> {
                binding.scrollView?.smoothScrollTo(0, 0)
            }
        }
    }

    private fun initLayout() {
        // 상단 ViewPager Parallax 동작
        binding.viewPager.setPageTransformer(
            ParallaxPageTransformer()
                .addViewToParallax(ParallaxPageTransformer.ParallaxTransformInformation(R.id.image_view, 2f, 2f))
                .addViewToParallax(ParallaxPageTransformer.ParallaxTransformInformation(R.id.text_title, 1.1f, 1.1f))
                .addViewToParallax(ParallaxPageTransformer.ParallaxTransformInformation(R.id.text_new, 1.5f, 1.5f))
        )
        // 관광지 리스트 간격
        binding.recyclerHome.addItemDecoration(HomeItemDecoration(context!!))
        // 필터 설정
        bottomSheetBehavior = BottomSheetBehavior.from(binding.linearBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        // Fab Hide
        binding.fab.hide()
    }

    private fun initListener() {
        // 전체 NestedScrollView 스크롤 리스너
        binding.scrollView.setOnScrollChangeListener(object : HomeOnScrollChangedListener() {
            override fun onScrollHalfUp() {
                if (binding.fab.isOrWillBeShown) binding.fab.hide()
//                if (binding.fab.visibility == View.VISIBLE) binding.fab.visibility = View.GONE
            }

            override fun onScrollHalfDown() {
                if (binding.fab.isOrWillBeHidden) binding.fab.show()
//                if (binding.fab.visibility == View.GONE) binding.fab.visibility = View.VISIBLE
            }
        })
        // 상단 ViewPager 페이지 전환 콜백
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.indicator.selectDot(position)
            }
        })
        // 필터 상태 변화 콜백
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if ((binding.viewAlphaBg.visibility == View.GONE && newState == BottomSheetBehavior.STATE_SETTLING) || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.viewAlphaBg.visibility = View.VISIBLE
                    isBottomSheetHidden = false
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    binding.viewAlphaBg.visibility = View.GONE
                    isBottomSheetHidden = true
                }
            }
        })

        binding.textFilter.setOnClickListener(this)
        binding.viewAlphaBg.setOnClickListener(this)
        binding.textBottomSheetCancel.setOnClickListener(this)
        binding.textBottomSheetConfirm.setOnClickListener(this)
        binding.textBottomSheetPopular.setOnClickListener(this)
        binding.textBottomSheetNew.setOnClickListener(this)
        binding.textBottomSheetName.setOnClickListener(this)
        binding.fab.setOnClickListener(this)
    }

    /**
     * Indicator 생성
     * @param count Indicator 갯수
     */
    private fun setIndicator(count: Int = 0) {
        // 원사이의 간격
        binding.indicator.setItemMargin(15)
        // 애니메이션 속도
        binding.indicator.setAnimDuration(300)
        // indecator 생성
        binding.indicator.createDotPanel(
            count,
            R.drawable.indicator_gray,
            R.drawable.indicator_white
        )
    }

    /**
     * 필터에서 인기, 최신, 제목 선택시 TextColor 변경
     * @param textView1 선택 된 TextView
     * @param textView2 선택 되지 않은 TextView
     * @param textView3 선택 되지 않은 TextView
     */
    private fun setTextColor(textView1: TextView, textView2: TextView, textView3: TextView) {
        textView1.setTextColor(context!!.resources.getColor(R.color.colorPrimaryDark))
        textView2.setTextColor(context!!.resources.getColor(R.color.colorFilterOff))
        textView3.setTextColor(context!!.resources.getColor(R.color.colorFilterOff))

        textView1.setBackgroundResource(R.drawable.bg_filter_on)
        textView2.setBackgroundResource(R.drawable.bg_filter_off)
        textView3.setBackgroundResource(R.drawable.bg_filter_off)
    }

    fun setBottomSheetHidden() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        isBottomSheetHidden = true
    }
}