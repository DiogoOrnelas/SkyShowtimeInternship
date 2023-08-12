package com.example.skyshowtime.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.SearchResultsBinding
import com.example.skyshowtime.ui.*
import com.example.skyshowtime.ui.response.Attribute
import com.example.skyshowtime.ui.response.SearchAsset


class MyResultsAdapter  internal constructor(private val items: List<SearchAsset>, private val listener : (Attribute)-> Unit):
        RecyclerView.Adapter<MyResultsAdapter.SearchResultsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultsViewHolder {
        val binding = SearchResultsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchResultsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultsViewHolder, position: Int) {
        Log.i("item: ", items[position].attributes.title)
        with(holder){
            val urls = getUrlByType(items[position].attributes)
            Glide.with(MyApplication.appContext)
                .load(urls)
                .placeholder(R.drawable.ic_skyshowtime_small_logo)
                .centerCrop()
                .into(binding.ivResults);

            itemView.setOnClickListener{
                listener.invoke(items[position].attributes)
            }
        }
    }

    override fun getItemCount() = items.count()

    private fun getUrlByType(item: Attribute) : String{
        var urlList = ""
        for (i in 0 until item.images.count()){
            if (item.images[i].type == "titleArt169"){
                urlList = item.images[i].url
            }
        }
        return urlList
    }

    inner class SearchResultsViewHolder(val binding : SearchResultsBinding)
        :RecyclerView.ViewHolder(binding.root)

}