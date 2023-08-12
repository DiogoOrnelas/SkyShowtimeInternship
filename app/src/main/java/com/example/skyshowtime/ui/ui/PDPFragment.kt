package com.example.skyshowtime.ui.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.FragmentHomeBinding
import com.example.skyshowtime.databinding.FragmentPdpBinding
import com.example.skyshowtime.ui.adapter.MyHighlightPagerAdapter
import com.example.skyshowtime.ui.adapter.MyPdpAdapter
import com.example.skyshowtime.ui.adapter.MyRecommendAdapter
import com.example.skyshowtime.ui.adapter.MyRecyclerViewAdapter
import com.example.skyshowtime.ui.response.Item
import com.example.skyshowtime.ui.response.Rail
import com.example.skyshowtime.ui.viewModel.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class PDPFragment : Fragment() {

    private val homeViewModel by lazy {
        ViewModelProvider( this )[HomeViewModel::class.java]
    }

    private var _binding : FragmentPdpBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPdpBinding.inflate(inflater, container, false)
        val view = binding.root

         val layoutManager =  GridLayoutManager(activity, resources.getInteger(R.integer.pdp_colums) )

        val args : PDPFragmentArgs by navArgs()
        val selectedItem =args.item
        val rcAdapter = MyPdpAdapter(selectedItem) { item ->
            val directions = PDPFragmentDirections.actionPdpFragmentToPlayerActivity(item)
            findNavController().navigate(directions)
        }
        homeViewModel.liveData.observe(viewLifecycleOwner) { homeResponse ->

            val nonEmptyRails = homeResponse.data.group.rails.filter{
                !it.items.isNullOrEmpty()
            }
            val items = getItems(nonEmptyRails)
            val related = items.filter {
                it.classification == selectedItem.classification
                        && it.title != selectedItem.title
            }

            val vpAdapter =  MyRecommendAdapter(related.slice(0..10).distinct()) { item ->
                val directions = PDPFragmentDirections.actionPdpFragmentSelf(item)
                findNavController().navigate(directions)
            }

            val concatAdapter = ConcatAdapter(rcAdapter, vpAdapter)
            binding.rcPDP.adapter = concatAdapter
            binding.rcPDP.layoutManager = layoutManager
            binding.pbPdp.isVisible = false
        }

        return view
    }

    override fun onResume() {
        val menuItem = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)    //Binding?
        (activity as AppCompatActivity).supportActionBar?.show()
        menuItem?.isVisible = false
        super.onResume()
    }

    override fun onDetach() {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onDetach()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pbPdp.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getContent()
    }


    private fun getItems(rails : List<Rail>) : List<Item>{
        val itemsList = mutableListOf<Item>()
        for (items in rails){
            for (i in 0 until (items.items!!.size))
            itemsList.add(items.items[i])
        }
        return itemsList
    }
}


