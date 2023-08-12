package com.example.skyshowtime.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skyshowtime.ui.response.SearchAsset
import com.example.skyshowtime.ui.network.SearchIdService
import com.example.skyshowtime.ui.network.SearchQueryService
import kotlinx.coroutines.*
import kotlin.Exception

class SearchQueryViewModel : ViewModel() {

    private val searchQueryService : SearchQueryService by lazy { SearchQueryService() }
    private val searchIdService : SearchIdService by lazy { SearchIdService() }

    val liveData = MutableLiveData< List<SearchAsset> > ()

     fun getContent(query : String){
        runBlocking {
            CoroutineScope (Dispatchers.Main).launch{
                try {

                    val searchResults = searchQueryService.allAssets(query)

                    val assets = searchResults.results.map {
                        async {
                            try {
                                return@async searchIdService.getAssetById(it.uuid)
                            }catch (e : Exception){
                                Log.i("Asset log: ", e.message.toString())
                                return@async null
                            }
                        }

                    }.awaitAll()

                    val nonEmptyAssets = assets.filterNotNull()

                    liveData.value = nonEmptyAssets.filter {
                        !it.attributes.images.isNullOrEmpty()
                    }
                }catch (e: Exception){
                    Log.i("Asset log: ", e.message.toString())
                }
            }
        }

    }

}