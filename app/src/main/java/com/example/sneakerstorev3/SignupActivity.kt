package com.example.sneakerstorev3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class SignupActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        dbHelper = DBHelper(this)

        val btnLogin = findViewById<Button>(R.id.btnSignToLogin)
        btnLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val btnSignup = findViewById<Button>(R.id.btnSignup)
        btnSignup.setOnClickListener {
            registerUser()
        }

    }

    fun registerUser() {
        val etUsername = findViewById<EditText>(R.id.inputSignUsername)
        val etPassword = findViewById<EditText>(R.id.inputSignPassword)
        val etFirstname = findViewById<EditText>(R.id.inputSignFirstname)
        val etLastname = findViewById<EditText>(R.id.inputSignLastname)
        val etTel = findViewById<EditText>(R.id.inputSignTel)
        val etAddress = findViewById<EditText>(R.id.inputSignAddress)

        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val firstname = etFirstname.text.toString()
        val lastname = etLastname.text.toString()
        val tel = etTel.text.toString()
        val address = etAddress.text.toString()

        // Check if any of the fields are empty
        if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || tel.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        } else {
            // All fields are filled, proceed with registration
            dbHelper.addUser(username, password, firstname, lastname, tel, address)

            // Send the user back to the Login page
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
        }
    }

}
