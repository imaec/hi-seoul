package com.imaec.hiseoul.retrofit

import com.imaec.hiseoul.model.AreaData
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
                    @Query("ServiceKey") ServiceKey: String = URLDecoder.decode("ltGo7k1Pwd%2ByDXKFfLY50ON1sAt6T4cKTYakVnqS960Los5TDyLpyWpo2UFGjulmBs9jspaMvwazKJLEttVfsQ%3D%3D", "UTF-8"),
                    @Query("areaCode") areaCode: String = "1",
                    @Query("listYN") listYN: String = "Y",
                    @Query("MobileOS") MobileOS: String = "AND",
                    @Query("MobileApp") MobileApp: String = "HiSeoul"): Observable<AreaData>
}