package com.example.skyshowtime.ui.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.FragmentHomeBinding
import com.example.skyshowtime.ui.viewModel.HomeViewModel
import com.example.skyshowtime.ui.adapter.MyHighlightPagerAdapter
import com.example.skyshowtime.ui.adapter.MyRecyclerViewAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {

    private val homeViewModel by lazy {
        ViewModelProvider( this )[HomeViewModel::class.java]
    }

    private var _binding : FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val layoutManager = LinearLayoutManager( activity, LinearLayoutManager.VERTICAL, false )

        homeViewModel.liveData.observe(viewLifecycleOwner) { homeResponse ->
            val rails = homeResponse.data.group.rails.subList(1,homeResponse.data.group.rails.count())
            val filteredRails = rails.filter{
                !it.items.isNullOrEmpty()
            }
            val vpAdapter = MyHighlightPagerAdapter(homeResponse.data.group.rails[0]) { item ->
                val directions = HomeFragmentDirections.actionHomeFragmentToPdpFragment(item )
                findNavController().navigate(directions)
            }
            val rcAdapter = MyRecyclerViewAdapter(filteredRails) { item ->
                 val directions = HomeFragmentDirections.actionHomeFragmentToPdpFragment(item)
                findNavController().navigate(directions)
            }
            val concatAdapter = ConcatAdapter(vpAdapter,rcAdapter)
            binding.rcHome.adapter = concatAdapter
            binding.rcHome.layoutManager = layoutManager
            binding.pbHome.isVisible = false
        }
        return view
    }

    override fun onResume() {
        val menuItem = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)   //Binding?
        menuItem?.isVisible = true
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getContent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pbHome.isVisible = true
    }
}


