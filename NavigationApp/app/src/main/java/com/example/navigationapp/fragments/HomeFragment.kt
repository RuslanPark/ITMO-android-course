package com.example.navigationapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationapp.R
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        var count = args.count
        view.textViewHome.text = count.toString()
        view.nextButtonHome.setOnClickListener{
            val action = HomeFragmentDirections.homeFragmentSelf(++count)
            it.findNavController().navigate(action)
        }

        return view
    }
}