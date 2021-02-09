package com.example.animationapp

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animationapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val animatorSet = AnimatorInflater.loadAnimator(binding.textView.context, R.animator.text_view_launch) as AnimatorSet
        animatorSet.setTarget(binding.textView)
        animatorSet.start()
    }
}