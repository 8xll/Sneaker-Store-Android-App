package com.example.sneakerstorev3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class SneakerStoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sneakerstore)

        val receivedBundle = intent.extras
        val id = receivedBundle?.getString("id")
        val bundle = Bundle()
        bundle.putString("id", id)

        val btnNavigation = findViewById<BottomNavigationView>(R.id.btnNavigation)
        btnNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigationProduct -> {
                    val productFragment = ProductFragment()
                    productFragment.arguments = bundle
                    replaceFragment(productFragment)
                }
                R.id.navigationCart -> {
                    val cartFragment = CartFragment()
                    cartFragment.arguments = bundle
                    replaceFragment(cartFragment)
                }
                R.id.navigationProfile -> {
                    val profileFragment = ProfileFragment()
                    profileFragment.arguments = bundle
                    replaceFragment(profileFragment)
                }
            }
            true
        }
        if (savedInstanceState == null) {
            val productFragment = ProductFragment()
            productFragment.arguments = bundle
            replaceFragment(productFragment)
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment, fragment.javaClass.simpleName)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
