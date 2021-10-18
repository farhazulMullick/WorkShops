package com.example.workshop.fragments

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workshop.MainActivity
import com.example.workshop.R
import com.example.workshop.adapter.WorkShopAdapter
import com.example.workshop.databinding.FragmentWorkShopBinding
import com.example.workshop.factory.WorkShopViewModelFactory
import com.example.workshop.listeners.WorkShopEnrollmentListener
import com.example.workshop.viewUtils.toast
import com.example.workshop.viewmodel.WorkShopVeiwModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkShopFragment : Fragment(), WorkShopEnrollmentListener {
    private var _binding : FragmentWorkShopBinding? = null
    private val binding get() = _binding!!

    //Veiwmodel

    private lateinit var viewModel: WorkShopVeiwModel
    private lateinit var workShopAdapter: WorkShopAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWorkShopBinding.inflate(layoutInflater)

        // ViewModel
        val factory = WorkShopViewModelFactory(activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory).get(WorkShopVeiwModel::class.java)
        viewModel.workShopEnrollmentListener = this
        setUpRecyclerView()
        fetchDataFromDatabse()
        viewModel.pageTitle.value = "Work Shops"
        return binding.root
    }

    private fun fetchDataFromDatabse() {
        viewModel.fetchAllWorkshops()
        viewModel._allWorkShops.observe(viewLifecycleOwner, Observer { workshops ->
            if( !workshops.isNullOrEmpty()){
                // if List is Empty
                Log.d(TAG, "fetchDataFromDatabse $workshops")
                workShopAdapter.setData(workshops)
            }
            Log.d(TAG, "fetchDataFromDatabse $workshops")
        })
    }

    private fun setUpRecyclerView() {
        workShopAdapter = WorkShopAdapter(viewModel)
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

    override fun onApplied(message: String) {
        lifecycleScope.launch(Dispatchers.Main.immediate){
            context?.toast(message)
        }
    }

    override fun onApplyFailed(message: String) {
        lifecycleScope.launch(Dispatchers.Main.immediate){
            context?.toast(message)
        }
    }
}