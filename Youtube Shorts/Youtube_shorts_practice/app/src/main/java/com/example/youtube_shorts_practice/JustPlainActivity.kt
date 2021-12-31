package com.example.youtube_shorts_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.youtube_shorts_practice.databinding.ActivityJustPlainBinding

class JustPlainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJustPlainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityJustPlainBinding.inflate(layoutInflater)
        var bindingView = binding.root
        setContentView(bindingView)

        binding.shortsBtn.setOnClickListener{

        }
    }
}