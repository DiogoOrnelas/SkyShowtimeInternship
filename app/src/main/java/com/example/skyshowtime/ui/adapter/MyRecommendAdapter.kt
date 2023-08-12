package com.example.skyshowtime.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.PdpRecomendationsBinding
import com.example.skyshowtime.ui.MyApplication
import com.example.skyshowtime.ui.response.Item

class MyRecommendAdapter internal constructor(private val items: List<Item>, private val listener : (Item)-> Unit):
    RecyclerView.Adapter<MyRecommendAdapter.PdpRecommendationsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdpRecommendationsViewHolder {
        val binding = PdpRecomendationsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PdpRecommendationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PdpRecommendationsViewHolder, position: Int) {
        with(holder){
            val urls = getUrlByType(items[position])
            Glide.with(MyApplication.appContext)
                .load(urls)
                .placeholder(R.drawable.ic_skyshowtime_small_logo)
                .centerCrop()
                .into(binding.ivRecommendations);
            itemView.setOnClickListener{
                listener.invoke(items[position])
            }
        }
    }

    override fun getItemCount() = items.count()

    private fun getUrlByType(item: Item) : String{
        var urlList = ""
        for (i in 0 until item.images.count()){
            if (item.images[i].type == "titleArt169"){
                urlList = item.images[i].url
            }
        }
        return urlList
    }

    inner class PdpRecommendationsViewHolder(val binding : PdpRecomendationsBinding) : RecyclerView.ViewHolder(binding.root)
}