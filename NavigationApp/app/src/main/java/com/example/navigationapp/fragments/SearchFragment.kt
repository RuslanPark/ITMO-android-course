package com.example.navigationapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationapp.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    companion object{
        var globalCount = 0
    }

    private val args: SearchFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        var count = args.count
        globalCount = count
        view.textViewSearch.text = count.toString()
        view.nextButtonSearch.setOnClickListener{
            val action = SearchFragmentDirections.searchFragmentSelf(++count)
            it.findNavController().navigate(action)
        }

        return view
    }
}