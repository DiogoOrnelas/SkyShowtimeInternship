package com.example.skyshowtime.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.PlayerPageBinding
import com.example.skyshowtime.ui.response.Item
import com.example.skyshowtime.ui.MyApplication

class MyPlayerAdapter internal constructor(private val item: Item, private val listener : (Item)-> Unit):
    RecyclerView.Adapter<MyPlayerAdapter.PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val binding = PlayerPageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayerViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        with(holder){
            val urls = getUrlByType(item)
            Glide.with(MyApplication.appContext)
                .load(urls)
                .placeholder(R.drawable.ic_skyshowtime_small_logo)
                .centerCrop()
                .into(binding.ivPlayer);
            binding.tvPlayerTitle.text = item.title
        }
    }

    override fun getItemCount() = 1

    inner class PlayerViewHolder (val binding : PlayerPageBinding) :RecyclerView.ViewHolder(binding.root)

    private fun getUrlByType(item: Item) : String{
        var urlList = ""
        for (i in 0 until item.images.count()){
            if (item.images[i].type == "landscape"){
                urlList = item.images[i].url
            }
        }
        return urlList
    }
}