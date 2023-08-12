package com.example.skyshowtime.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skyshowtime.ui.response.HomeResponse
import com.example.skyshowtime.ui.network.NetworkService
import kotlinx.coroutines.*
import java.lang.Exception


class HomeViewModel :ViewModel() {


    private val networkService : NetworkService by lazy { NetworkService() }

    val liveData = MutableLiveData<HomeResponse> ()

    fun getContent(){
        runBlocking {
            CoroutineScope (Dispatchers.Main).launch {
                try {
                    val assets = networkService.allAssets()
                    Log.i("Asset log: ", assets.toString())
                    liveData.value = assets

                } catch (e: Exception) {
                    Log.i("Asset log: ", e.message.toString())
                }
            }
        }
    }

}