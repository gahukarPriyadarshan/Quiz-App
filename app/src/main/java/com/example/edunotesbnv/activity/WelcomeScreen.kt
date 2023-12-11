package com.example.edunotesbnv.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.edunotesbnv.databinding.ActivityWelcomeScreenBinding

class WelcomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener{
            val intent = Intent(this, MainActivity :: class.java)
            startActivity(intent)
            finish()
        }
    }
}