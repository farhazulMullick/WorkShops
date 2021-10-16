package com.example.workshop.loginsignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.workshop.R
import com.example.workshop.databinding.ActivityCreateAccBinding

class CreateAccActivity : AppCompatActivity() {

    private var _binding: ActivityCreateAccBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_create_acc)

        binding.tvLoginHere.setOnClickListener{
            Intent(this, LoginActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
    }
}