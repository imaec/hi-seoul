package com.imaec.hiseoul.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.imaec.hiseoul.R
import com.imaec.hiseoul.databinding.FragmentHomeBinding
import com.imaec.hiseoul.util.HomeItemDecoration
import com.imaec.hiseoul.util.ParallaxPageTransformer
import com.imaec.hiseoul.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment2 : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

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
            setIndicator(it.size)
        })
        binding.viewPager.setPageTransformer(
            ParallaxPageTransformer()
                .addViewToParallax(ParallaxPageTransformer.ParallaxTransformInformation(R.id.image_view, 2f, 2f))
                .addViewToParallax(ParallaxPageTransformer.ParallaxTransformInformation(R.id.text_title, 1.1f, 1.1f))
                .addViewToParallax(ParallaxPageTransformer.ParallaxTransformInformation(R.id.text_new, 1.5f, 1.5f))
        )
    }

    private fun initLayout() {
        binding.recyclerHome.addItemDecoration(HomeItemDecoration(context!!))

        bottomSheetBehavior = BottomSheetBehavior.from(binding.linearBottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun initListener() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.indicator.selectDot(position)
            }
        })

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if ((binding.viewAlphaBg.visibility == View.GONE && newState == BottomSheetBehavior.STATE_SETTLING)
                    || newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    binding.viewAlphaBg.visibility = View.VISIBLE
                } else if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    binding.viewAlphaBg.visibility = View.GONE
                }
            }
        })
    }

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
}