package com.example.navigationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.android.navigationadvancedsample.setupWithNavController
import com.example.navigationapp.fragments.HomeFragment
import com.example.navigationapp.fragments.ListFragment
import com.example.navigationapp.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            bottom_navigation.selectedItemId = R.id.home_fragment
        } else {
            bottom_navigation.selectedItemId = savedInstanceState.getInt("lastFragment")
        }

        val navGraphIds = listOf(
            R.navigation.home_fragment_navigation,
            R.navigation.list_fragment_navigation,
            R.navigation.search_fragment_navigation
        )

        val controller = bottom_navigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )
        currentNavController = controller


        savedInstanceState?.let {
            bottom_navigation.selectedItemId = it.getInt("lastFragment")
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("lastFragment", bottom_navigation.selectedItemId)
        super.onSaveInstanceState(outState)
    }
}