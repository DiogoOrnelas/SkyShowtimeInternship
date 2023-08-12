package com.example.skyshowtime.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.PageCellBinding
import com.example.skyshowtime.ui.response.Item
import com.example.skyshowtime.ui.MyApplication

class MyPagerAdapter internal constructor(private val items: List<Item>, private val listener : (Item)-> Unit):
    RecyclerView.Adapter<MyPagerAdapter.PageCellViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageCellViewHolder {
        val binding = PageCellBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PageCellViewHolder(binding)
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: PageCellViewHolder, position: Int) {
        with(holder){
            val urls = getUrlByType(items[position])
            Glide.with(MyApplication.appContext)
                .load(urls)
                .placeholder(R.drawable.ic_skyshowtime_small_logo)
                .centerCrop()
                .into(binding.ivPage);

            holder.itemView.setOnClickListener{
                listener.invoke(items[position])
            }
        }
    }

    inner class PageCellViewHolder( val binding: PageCellBinding) :RecyclerView.ViewHolder(binding.root)

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
