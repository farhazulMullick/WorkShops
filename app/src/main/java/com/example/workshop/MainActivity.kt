package com.example.workshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.workshop.databinding.ActivityMainBinding
import com.example.workshop.factory.WorkShopViewModelFactory
import com.example.workshop.loginsignup.LoginActivity
import com.example.workshop.viewmodel.WorkShopVeiwModel
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: WorkShopVeiwModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val factory = WorkShopViewModelFactory(this.application)
        viewModel = ViewModelProvider(this, factory).get(WorkShopVeiwModel::class.java)

        setUpDatabase()
        binding.navView.setNavigationItemSelectedListener(this)

        setUpDrawerHandle()
    }

    private fun setUpDatabase() {
       viewModel.addAllWorkShops()
    }

    fun setUpDrawerHandle(){
        binding.toolbar.setNavigationOnClickListener {

            binding.drawerLayout.apply {
                if (this.isDrawerOpen(GravityCompat.START))
                    this.closeDrawer(GravityCompat.START)
                else
                    this.openDrawer(GravityCompat.START)
            }
        }
    }

    override fun onBackPressed() {

        binding.drawerLayout.apply {
            if (this.isDrawerOpen(GravityCompat.START))
                this.closeDrawer(GravityCompat.START)
            else
                super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.workShopFragment ->{
                findNavController(R.id.workshopnavhostfragment).navigate(R.id.workShopFragment)
            }
            R.id.loginFragment ->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.dashBoard->{
                findNavController(R.id.workshopnavhostfragment).navigate(R.id.dashBoardFragment)
            }
        }
        binding.drawerLayout.apply {
            if (this.isDrawerOpen(GravityCompat.START))
                this.closeDrawer(GravityCompat.START)
            else
                this.openDrawer(GravityCompat.START)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}