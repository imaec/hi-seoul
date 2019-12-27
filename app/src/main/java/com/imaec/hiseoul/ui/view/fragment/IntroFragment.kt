package com.imaec.hiseoul.ui.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.imaec.hiseoul.R
import com.imaec.hiseoul.retrofit.HiSeoulService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_intro.*

class IntroFragment : Fragment() {

    private lateinit var compositeDisposable: CompositeDisposable
    private var contentId = 0
    private var contentTypeId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.fragment_intro, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun init() {
        compositeDisposable = CompositeDisposable()

        arguments?.let {
            contentId = it.getInt("contentId", 0)
            contentTypeId = it.getInt("contentTypeId", 0)
        }
        getIntro()
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun getIntro() {
        val service = HiSeoulService.instance
        val callGetIntro = service.callGetIntro(contentId)
        callGetIntro
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.body.items.item
            }
            .subscribe({
                textIntroSummary.text = it.overview
                if (it.addr1 == null) {
                    textIntroAddress.text = "주소정보없음"
                } else {
                    textIntroAddress.text = "${it.addr1} ${it.addr2}".replace(" null", "")
                }
                textIntroTel.text = it.tel
                textIntroHomepage.text = it.homepage
            }, {
                Log.d("it2 :::: ", it.message)
            }, {

            }, {
                compositeDisposable.add(it)
            })
    }
}