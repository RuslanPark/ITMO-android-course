package com.example.navigationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.navigationapp.databinding.ActivityMainBinding
import com.ruslanpark.kakeiboapp.main.setupWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.home_fragment
        } else {
            binding.bottomNavigation.selectedItemId = savedInstanceState.getInt("lastFragment")
        }

        val navGraphIds = listOf(
            R.navigation.home_fragment_navigation,
            R.navigation.list_fragment_navigation,
            R.navigation.search_fragment_navigation
        )

        val controller = binding.bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        currentNavController = controller


        savedInstanceState?.let {
            binding.bottomNavigation.selectedItemId = it.getInt("lastFragment")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("lastFragment", binding.bottomNavigation.selectedItemId)
        super.onSaveInstanceState(outState)
    }
}