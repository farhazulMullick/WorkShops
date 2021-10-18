package com.example.workshop.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workshop.R
import com.example.workshop.adapter.DashBoardAdapter
import com.example.workshop.databinding.FragmentDashBoardBinding
import com.example.workshop.factory.WorkShopViewModelFactory
import com.example.workshop.loginsignup.CreateAccActivity
import com.example.workshop.loginsignup.LoginActivity
import com.example.workshop.viewmodel.WorkShopVeiwModel

class DashBoardFragment : Fragment() {
    companion object{
        const val TAG ="DashBoardFragment"
    }

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkShopVeiwModel
    private lateinit var dashBoardAdapter: DashBoardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashBoardBinding.inflate(layoutInflater)
        val factory = WorkShopViewModelFactory(activity?.application!!)
        viewModel = ViewModelProvider(requireActivity(), factory).get(WorkShopVeiwModel::class.java)


        setUpRecyclerView()
        viewModel.pageTitle.value = "DashBoard"
        return binding.root
    }

    private fun setUpRecyclerView() {
        dashBoardAdapter = DashBoardAdapter(viewModel)
        binding.rvMyworkshops.apply {
            adapter = dashBoardAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    fun signInState(){
        binding.signInLayout.visibility = View.VISIBLE
        binding.signOutLayout.visibility = View.GONE

        viewModel.getUserInfo()
        viewModel.userName.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()){
                Log.d(TAG, "username $it")
                binding.etUserName.text = it
            }
        })
    }

    fun signOutState(){
        binding.signInLayout.visibility = View.GONE
        binding.signOutLayout.visibility = View.VISIBLE
        binding.emptyListState.visibility = View.GONE

        binding.btnLogin.setOnClickListener{
            Intent(requireContext(), LoginActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.btnCreateAcc.setOnClickListener{
            Intent(requireContext(), CreateAccActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    fun showEmptyListState(){
        binding.emptyListState.visibility = View.VISIBLE
        binding.enrolledWorkshopList.visibility = View.GONE

        binding.btnApply.setOnClickListener {
            findNavController().navigate(R.id.action_dashBoardFragment_to_workShopFragment)
        }
    }

    fun hideEmptyListState(){
        binding.emptyListState.visibility = View.GONE
        binding.enrolledWorkshopList.visibility = View.VISIBLE
    }



    override fun onStart() {
        super.onStart()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.getUserId()
        viewModel.userId.observe(viewLifecycleOwner, Observer { userId ->
            if (userId > 0){
                signInState()
                viewModel.fetchAppliedWorkShops().observe(viewLifecycleOwner, Observer {
                    if (it.isNullOrEmpty()){
                        Log.d(TAG, "No Enrollments")
                        showEmptyListState()
                    }
                    else{
                        hideEmptyListState()
                    }
                    Log.d(TAG, "enrolled Workshops ${it.size}")
                    dashBoardAdapter.setData(it)

                })
            }
            else{
                signOutState()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}