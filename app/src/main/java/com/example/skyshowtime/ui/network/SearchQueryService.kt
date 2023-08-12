package com.example.skyshowtime.ui.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.skyshowtime.ui.MyApplication
import com.example.skyshowtime.ui.response.SearchQueryResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class SearchQueryService {
    companion object {
        val HEADER_PROVIDER = "SKYSHOWTIME"
        val HEADER_PROPOSITION = "SKYSHOWTIME"
        val HEADER_DEVICE = "MOBILE"
        val HEADER_PLATFORM = "ANDROID"
        val HEADER_ACTIVE_TERRITORY ="NL"
        val HEADER_TERRITORY = "NL"

    }

    private val okHttpClient by lazy {
        val interceptor = OkHttpClient.Builder()
        interceptor.addInterceptor(ChuckerInterceptor(MyApplication.appContext))
        return@lazy interceptor.build()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://suggest.disco.skyshowtime.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val assetService = retrofit.create(AssetService::class.java)

    suspend fun allAssets(query: String): SearchQueryResponse {
        return assetService.getAllAssets(
            HEADER_ACTIVE_TERRITORY,
            HEADER_DEVICE,
            HEADER_PLATFORM,
            HEADER_PROPOSITION,
            HEADER_PROVIDER,
            HEADER_TERRITORY,
            query,
        )
    }

    interface AssetService {
        @GET("/suggest/v1/stb/home/0/0/0?limit=40&entitytype=programme&entitytype=series&contentFormat=longform")
        suspend fun getAllAssets(
            @Header("x-skyott-activeterritory") activeTerritory: String,
            @Header("x-skyott-device") device: String,
            @Header("x-skyott-platform") platform: String,
            @Header("x-skyott-proposition") proposition: String,
            @Header("x-skyott-provider") provider: String,
            @Header("x-skyott-territory") territory: String,
            @Query("term") query : String
        ): SearchQueryResponse
    }
}


