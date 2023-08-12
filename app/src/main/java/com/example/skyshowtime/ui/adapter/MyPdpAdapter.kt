package com.example.skyshowtime.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.PdpBinding
import com.example.skyshowtime.ui.response.Item
import com.example.skyshowtime.ui.MyApplication

class MyPdpAdapter internal constructor(private val item: Item, private val listener: (Item) -> Unit):
    RecyclerView.Adapter<MyPdpAdapter.PDPViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PDPViewHolder {
        val binding = PdpBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PDPViewHolder(binding)
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: PDPViewHolder, position: Int) {
        with(holder){
            val header = getUrlByType(item, "landscape")
            val icon = getUrlByType(item, "titleArt169")
            Glide.with(MyApplication.appContext)
                .load(header)
                .placeholder(R.drawable.ic_skyshowtime_small_logo)
                .centerCrop()
                .into(binding.ivHeader);

            Glide.with(MyApplication.appContext)
                .load(icon)
                .placeholder(R.drawable.ic_skyshowtime_small_logo)
                .centerCrop()
                .into(binding.ivIcon);
            binding.tvDescription.text =  item.synopsisLong
            binding.tvTitle.text = item.title
            binding.btWatch.setOnClickListener{
                listener.invoke(item)
            }
        }
    }

    inner class PDPViewHolder( val binding: PdpBinding) :RecyclerView.ViewHolder(binding.root)

    private fun getUrlByType(item: Item, filter : String) : String{
        var url = ""
        for (i in 0 until item.images.count()){
            if (item.images[i].type == filter){
                url = item.images[i].url
            }
        }
        return url
    }
}