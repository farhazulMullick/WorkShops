package com.example.workshop.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workshop.R
import com.example.workshop.adapter.WorkShopAdapter
import com.example.workshop.databinding.FragmentWorkShopBinding
import com.example.workshop.factory.WorkShopViewModelFactory
import com.example.workshop.viewmodel.WorkShopVeiwModel

class WorkShopFragment : Fragment() {
    private var _binding : FragmentWorkShopBinding? = null
    private val binding get() = _binding!!

    //Veiwmodel
    private val factory = WorkShopViewModelFactory(requireContext() as Application)
    private lateinit var viewModel: WorkShopVeiwModel

    private lateinit var workShopAdapter: WorkShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWorkShopBinding.inflate(layoutInflater)

        // ViewModel
        viewModel = ViewModelProvider(requireActivity(), factory).get(WorkShopVeiwModel::class.java)

        fetchDataFromDatabse()
        setUpRecyclerView()
        return binding.root
    }

    private fun fetchDataFromDatabse() {
        viewModel.fetchAllWorkshops()
        viewModel._allWorkShops.observe(viewLifecycleOwner, Observer { workshops ->
            if( workshops.isNullOrEmpty()){
                // if List is Empty
            }
            workShopAdapter.setData(workshops)
        })
    }

    private fun setUpRecyclerView() {
        workShopAdapter = WorkShopAdapter()
        binding.rvWorkshop.apply {
            adapter = workShopAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        const val TAG = "WorkShopFrag"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}