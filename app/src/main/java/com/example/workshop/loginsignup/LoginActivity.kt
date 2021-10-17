package com.example.workshop.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.workshop.R
import com.example.workshop.databinding.ActivityLoginBinding
import com.example.workshop.factory.AuthViewModelFactory
import com.example.workshop.listeners.AuthListener
import com.example.workshop.viewUtils.toast
import com.example.workshop.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity(), AuthListener {

    //databindnig
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    override fun onAuthStarted() {
    }

    override fun onAuthFailed(message: String) {
       lifecycleScope.launch(Dispatchers.Main.immediate) {
           this@LoginActivity.toast(message)
       }
    }

    override fun onAuthCompleted() {
        lifecycleScope.launch(Dispatchers.Main.immediate){
            applicationContext.toast("Welcome!")
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val factory = AuthViewModelFactory(this.application)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        binding.viewModel = viewModel
        viewModel.authListener = this

        binding.tvCreateAcc.setOnClickListener{
            val intent = Intent(this, CreateAccActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}