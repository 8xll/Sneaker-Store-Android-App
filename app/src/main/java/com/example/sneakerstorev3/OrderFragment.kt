package com.example.sneakerstorev3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class OrderFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_order, container, false)

        val id = arguments?.getString("id")
        val dbHelper = DBHelper(requireContext())
        dbHelper.deleteCartItemsByUserId(id.toString().toInt())

        return rootView
    }
}