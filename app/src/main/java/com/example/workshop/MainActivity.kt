package com.example.workshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import com.example.workshop.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setUpDrawerHandle()
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
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}