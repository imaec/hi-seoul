package com.imaec.hiseoul.retrofit

import com.imaec.hiseoul.model.IntroData
import com.imaec.hiseoul.model.TourData
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

interface HiSeoulService {

    companion object {
        const val SERVICE_URL = "http://api.visitkorea.or.kr/"
        const val SERVICE_KEY = "tSjNUwbzL1FGAL7B8CoZrSCuwzWZ7GO1ton76vaKADWKkcurIK8ZuydB0BWckJ17wLNPYEtC3BTzw2A4%2Bxq1pQ%3D%3D"
        private val client = OkHttpClient.Builder().apply {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            addInterceptor(httpLoggingInterceptor.apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            connectTimeout(30, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        val instance = retrofit.create(HiSeoulService::class.java)!!
    }

    @GET("openapi/service/rest/KorService/areaBasedList")
    fun callGetList(@Query("contentTypeId") contentTypeId: Int,
                    @Query("arrange") arrange: String,
                    @Query("numOfRows") numOfRows: Int = 100,
                    @Query("pageNo") pageNo: Int = 1,
                    @Query("ServiceKey") ServiceKey: String = URLDecoder.decode(SERVICE_KEY, "UTF-8"),
                    @Query("areaCode") areaCode: String = "1",
                    @Query("listYN") listYN: String = "Y",
                    @Query("MobileOS") MobileOS: String = "AND",
                    @Query("MobileApp") MobileApp: String = "HiSeoul"): Observable<TourData>

    @GET("openapi/service/rest/KorService/detailImage")
    fun callGetImage(@Query("contentId") contentId: Int,
                     @Query("contentTypeId") contentTypeId: Int,
                     @Query("imageYN") imageYN: String = "Y",
                     @Query("ServiceKey") ServiceKey: String = URLDecoder.decode(SERVICE_KEY, "UTF-8"),
                     @Query("MobileOS") MobileOS: String = "AND",
                     @Query("MobileApp") MobileApp: String = "HiSeoul"): Observable<TourData>

    @GET("openapi/service/rest/KorService/detailCommon")
    fun callGetIntro(@Query("contentId") contentId: Int,
                     @Query("defaultYN") defaultYN: String = "Y",
                     @Query("addrinfoYN") addrinfoYN: String = "Y",
                     @Query("mapinfoYN") mapinfoYN: String = "Y",
                     @Query("overviewYN") overviewYN: String = "Y",
                     @Query("MobileOS") MobileOS: String = "AND",
                     @Query("MobileApp") MobileApp: String = "HiSeoul"): Observable<IntroData>
}