package com.example.workshop.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.workshop.MainActivity
import com.example.workshop.R
import com.example.workshop.databinding.ActivityCreateAccBinding
import com.example.workshop.factory.AuthViewModelFactory
import com.example.workshop.listeners.AuthListener
import com.example.workshop.viewUtils.toast
import com.example.workshop.viewUtils.toastLong
import com.example.workshop.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccActivity : AppCompatActivity(), AuthListener {

    private var _binding: ActivityCreateAccBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AuthViewModel

    override fun onAuthStarted() {
    }

    override fun onAuthFailed(message: String) {
        this.toastLong(message)
    }

    override fun onAuthCompleted() {
        lifecycleScope.launch(Dispatchers.Main) {
            applicationContext.toast("Successfully created Account")
            Intent(this@CreateAccActivity, MainActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_create_acc)

        val factory = AuthViewModelFactory(this.application)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewModel.authListener = this

        binding.viewModel = viewModel
        binding.lifecycleOwner = this



        binding.tvLoginHere.setOnClickListener{
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }


}