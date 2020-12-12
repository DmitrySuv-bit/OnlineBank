package com.example.online_bank.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.online_bank.R

class WelcomeFragment : Fragment() {
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.findViewById<Button>(R.id.okButton).setOnClickListener {
//            view.findNavController().navigate(R.id.action_welcomeFragment_to_homeFragment)
//            Log.d("sgfsf", view.findNavController().graph.toString())
//        }
    }
}