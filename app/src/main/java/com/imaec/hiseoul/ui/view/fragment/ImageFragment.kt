package com.imaec.hiseoul.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.imaec.hiseoul.R
import kotlinx.android.synthetic.main.fragment_image.*

class ImageFragment : Fragment() {

    private var img: String = ""
    private var title: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_image, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        arguments?.let {
            img = it.getString("img") ?: ""
            title = it.getString("title") ?: ""
        }
        Glide.with(this)
            .load(if (img == "") R.mipmap.ic_launcher else img)
            .into(imageView)
        textImage.text = title
        if (title == "") textImageNew.visibility = View.GONE
    }
}