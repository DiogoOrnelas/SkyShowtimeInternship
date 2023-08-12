package com.example.skyshowtime.ui.ui

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.FragmentSearchBinding
import com.example.skyshowtime.ui.response.Item
import com.example.skyshowtime.ui.viewModel.SearchQueryViewModel
import com.example.skyshowtime.ui.adapter.MyResultsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment(){

    private val searchQueryViewModel by lazy {
        ViewModelProvider( this )[SearchQueryViewModel::class.java]
    }

    private var _binding : FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        val layoutManager =  GridLayoutManager(activity, resources.getInteger(R.integer.search_colums))


        val progressBar = activity?.findViewById<ProgressBar>(R.id.pbMain)  //binding???

        searchQueryViewModel.liveData.observe(viewLifecycleOwner) { attributes ->
            val rcAdapter = MyResultsAdapter(attributes) { attribute ->

                val item = Item (
                    attribute.programmeUuid?: "",
                    attribute.title,
                    attribute.classification[0],
                    attribute.images,
                    attribute.synopsisLong)

               val directions = SearchFragmentDirections.actionSearchFragmentToPdpFragment(item)
                 findNavController().navigate(directions)
            }

            binding.rcResults.adapter = rcAdapter
            binding.rcResults.layoutManager = layoutManager
            binding.tvEmpty.isVisible = attributes.isNullOrEmpty()
            if (attributes.isNullOrEmpty()) binding.tvEmpty.text = "There are no results at this moment"
            progressBar?.isVisible = false
        }


        binding.idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                progressBar?.isVisible = true
                hideKeyboard()
                search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (binding.idSV.query.isNullOrEmpty()) {
                    return true
                }
                return true
            }
        })

        return view
    }

    override fun onResume() {
        val menuItem = activity?.findViewById<BottomNavigationView>(R.id.bottomNav)   //Binding?
        menuItem?.isVisible = true
        super.onResume()
        binding.idSV.clearFocus()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun search(query: String){
        searchQueryViewModel.getContent(query)
    }

    fun hideKeyboard() {
        val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }




}



