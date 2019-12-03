package com.imaec.hiseoul

import android.view.View
import androidx.viewpager.widget.ViewPager

class ParallaxPageTransformer : ViewPager.PageTransformer {

    private var mViewsToParallax = ArrayList<ParallaxTransformInformation>()

    constructor()

    constructor(viewsToParallax: ArrayList<ParallaxTransformInformation>) {
        mViewsToParallax = viewsToParallax
    }

    fun addViewToParallax(viewInfo: ParallaxTransformInformation): ParallaxPageTransformer {
        mViewsToParallax.add(viewInfo)
        return this
    }

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width

        when {
            position < -1 -> // This page is way off-screen to the left.
                page.alpha = 1f
            position <= 1 -> // [-1,1]
                for (parallaxTransformInformation in mViewsToParallax) {
                    applyParallaxEffect(
                        page, position, pageWidth, parallaxTransformInformation,
                        position > 0
                    )
                }
            else -> // This page is way off-screen to the right.
                page.alpha = 1f
        }
    }

    private fun applyParallaxEffect(view: View, position: Float, pageWidth: Int, information: ParallaxTransformInformation, isEnter: Boolean) {
        if (information.isValid && view.findViewById<View>(information.resource) != null) {
            if (isEnter && !information.isEnterDefault) {
                view.findViewById<View>(information.resource).translationX =
                    -position * (pageWidth / information.parallaxEnterEffect)
            } else if (!isEnter && !information.isExitDefault) {
                view.findViewById<View>(information.resource).translationX =
                    -position * (pageWidth / information.parallaxExitEffect)
            }
        }
    }

    class ParallaxTransformInformation(resource: Int, parallaxEnterEffect: Float, parallaxExitEffect: Float) {

        internal var resource = -1
        internal var parallaxEnterEffect = 1f
        internal var parallaxExitEffect = 1f

        val isValid: Boolean
            get() = parallaxEnterEffect != 0f && parallaxExitEffect != 0f && resource != -1

        val isEnterDefault: Boolean
            get() = parallaxEnterEffect == PARALLAX_EFFECT_DEFAULT

        val isExitDefault: Boolean
            get() = parallaxExitEffect == PARALLAX_EFFECT_DEFAULT

        init {
            this.resource = resource
            this.parallaxEnterEffect = parallaxEnterEffect
            this.parallaxExitEffect = parallaxExitEffect
        }

        companion object {

            val PARALLAX_EFFECT_DEFAULT = -101.1986f
        }
    }
}