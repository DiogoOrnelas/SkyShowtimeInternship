package com.example.skyshowtime.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.ItemCellBinding
import com.example.skyshowtime.ui.response.Item
import com.example.skyshowtime.ui.MyApplication

class MyRailAdapter internal constructor(private val items: List<Item>, private val listener : (Item)-> Unit):
    RecyclerView.Adapter<MyRailAdapter.ItemCellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemCellViewHolder {
       val binding = ItemCellBinding
           .inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemCellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemCellViewHolder, position: Int) {
        with(holder){
            val urls = getUrlByType(items[position])
            Glide.with(MyApplication.appContext)
                .load(urls)
                .placeholder(R.drawable.ic_skyshowtime_small_logo)
                .centerCrop()
                .into(binding.ivItem);
            itemView.setOnClickListener{
                listener.invoke(items[position])
            }
        }
    }

    override fun getItemCount() = items.count()

    inner class ItemCellViewHolder(val binding: ItemCellBinding) :RecyclerView.ViewHolder(binding.root)

    private fun getUrlByType(item: Item) : String{
        var urlList = ""
        for (i in 0 until item.images.count()){
            if (item.images[i].type == "titleArt169"){
                urlList = item.images[i].url
            }
        }
        return urlList
    }
}

