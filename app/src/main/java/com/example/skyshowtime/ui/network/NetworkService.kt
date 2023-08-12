package com.example.skyshowtime.ui.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.skyshowtime.ui.response.HomeResponse
import com.example.skyshowtime.ui.MyApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import java.util.*

class NetworkService {

    companion object{
        val HEADER_PROVIDER = "SKYSHOWTIME"
        val HEADER_PROPOSITION = "SKYSHOWTIME"
        val HEADER_ACTIVE_TERRITORY ="NL"
        val HEADER_DEVICE = "MOBILE"
        val HEADER_LANGUAGE = Locale.getDefault().toLanguageTag()  //Gets the device Language (REQUEST DOESN'T EXIST FOR SOME LANGUAGES)
        val HEADER_PLATFORM ="ANDROID"
        val HEADER_TERRITORY ="US"
    }

    private val okHttpClient by lazy {
        val interceptor = OkHttpClient.Builder()
        interceptor.addInterceptor(ChuckerInterceptor(MyApplication.appContext))
        return@lazy interceptor.build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://mobile.clients.skyshowtime.com")
        .client( okHttpClient )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val assetService = retrofit.create(AssetService::class.java)

    suspend fun allAssets(): HomeResponse  {
        return assetService.getAllAssets(
            HEADER_ACTIVE_TERRITORY,
            HEADER_DEVICE,
            HEADER_LANGUAGE,
            HEADER_PLATFORM,
            HEADER_PROPOSITION,
            HEADER_PROVIDER,
            HEADER_TERRITORY)
    }
}

interface AssetService {
    @GET("/bff/sections/v1?segment=all_premium_users&slug=%2Fhome&filter=byw")
    suspend fun getAllAssets(
        @Header ("x-skyott-activeterritory") activeTerritory : String,
        @Header ("x-skyott-device") device : String,
        @Header ("x-skyott-language") language : String,
        @Header ("x-skyott-platform") platform : String,
        @Header ("x-skyott-proposition") proposition : String,
        @Header ("x-skyott-provider") provider : String,
        @Header ("x-skyott-territory") territory : String,
    ) : HomeResponse
}