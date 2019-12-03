package com.imaec.hiseoul.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.imaec.hiseoul.R
import kotlinx.android.synthetic.main.fragment_new.*

class NewFragment : Fragment() {

    private var img: String = ""
    private var title: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_new, container, false)

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
            .load(img)
            .into(imageNew)
        textNew.text = title
    }
}