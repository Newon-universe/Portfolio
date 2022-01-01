package com.example.thread_practice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.thread_practice.databinding.ActivityStartBinding

class startActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nextActivity = Intent(this, MainActivity::class.java)

        var startImage = binding.gameStart
        startImage.setOnClickListener {
            startActivity(nextActivity)
        }
    }
}