package com.example.skyshowtime.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.RailCellBinding
import com.example.skyshowtime.ui.response.Item
import com.example.skyshowtime.ui.MyApplication
import com.example.skyshowtime.ui.response.Rail
import com.example.skyshowtime.ui.RailItemDecorator

class MyRecyclerViewAdapter internal constructor(private val rails: List<Rail>, private val listener : (Item)-> Unit):
    RecyclerView.Adapter<MyRecyclerViewAdapter.RailCellViewHolder>() {

    private val decorator = RailItemDecorator(MyApplication.appContext.resources.getDimension(R.dimen.horizontal_spacing))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RailCellViewHolder {
        val binding = RailCellBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return RailCellViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RailCellViewHolder, position: Int) {
        with(holder){
            binding.tvPosterTitle.text = rails[position].title
            val layoutManager = LinearLayoutManager(
                holder.itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            val adapter = MyRailAdapter(rails[position].items ?: emptyList(), listener)
            binding.rcRail.removeItemDecoration(decorator)  //Is there another way to do this?
            binding.rcRail.addItemDecoration(decorator)
            binding.rcRail.adapter = adapter
            binding.rcRail.layoutManager = layoutManager
        }
    }

    override fun getItemCount() = rails.count()

    inner class RailCellViewHolder(val binding: RailCellBinding)
        :RecyclerView.ViewHolder(binding.root)
}

