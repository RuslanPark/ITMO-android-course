package com.example.navigationapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.navigationapp.R
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    companion object{
        var globalCount = 0
    }

    private val args: ListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        var count = args.count
        globalCount = count
        view.textViewList.text = count.toString()
        view.nextButtonList.setOnClickListener{
            val action = ListFragmentDirections.listFragmentSelf(++count)
            it.findNavController().navigate(action)
        }

        return view
    }
}