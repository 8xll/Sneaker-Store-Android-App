package com.example.sneakerstorev3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        val Logout = rootView.findViewById<Button>(R.id.Logout)
        Logout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
        }

        val lastnameProfile = rootView.findViewById<TextView>(R.id.lastnameProfile)
        val firstnameProfile = rootView.findViewById<TextView>(R.id.firstnameProfile)
        val telProfile = rootView.findViewById<TextView>(R.id.telProfile)
        val addressProfile = rootView.findViewById<TextView>(R.id.addressProfile)

        val id = arguments?.getString("id") // ค่า ID ควรเป็น String หรือ Int?

        val dbHelper = DBHelper(requireContext())

        val cursor = dbHelper.getUserDetails(id.toString())

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val firstname =
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.FIRSTNAME_COL))
                val lastname = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.LASTNAME_COL))
                val tel = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.TEL_COL))
                val address = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.ADDRESS_COL))

                firstnameProfile.text = firstname
                lastnameProfile.text = lastname
                telProfile.text = tel
                addressProfile.text = address
            }
            cursor.close()
        }

        dbHelper.close()

        return rootView
    }
}