package com.example.skyshowtime.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skyshowtime.databinding.HighlightPagerBinding
import com.example.skyshowtime.ui.response.Item
import com.example.skyshowtime.ui.response.Rail
import com.google.android.material.tabs.TabLayoutMediator

class MyHighlightPagerAdapter internal constructor(private val rails: Rail, private val listener : (Item)-> Unit):
    RecyclerView.Adapter<MyHighlightPagerAdapter.HighlightViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighlightViewHolder {
        val binding = HighlightPagerBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HighlightViewHolder(binding)
    }

    override fun getItemCount() = 1

    override fun onBindViewHolder(holder: HighlightViewHolder, position: Int) {
        with(holder){
            if(!rails.items.isNullOrEmpty()) {
                val adapter = MyPagerAdapter(rails.items,listener)

                binding.vpHighlights.adapter = adapter
                TabLayoutMediator(binding.tabLayout, binding.vpHighlights) { _, _ ->

                }.attach()
            }
        }
    }

    inner class HighlightViewHolder(val binding: HighlightPagerBinding) :RecyclerView.ViewHolder(binding.root)
}