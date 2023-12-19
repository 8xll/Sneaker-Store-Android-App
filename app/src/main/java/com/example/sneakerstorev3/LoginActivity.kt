package com.example.sneakerstorev3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    // Initialize your DBHelper instance
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DBHelper(this) // Initialize your DBHelper with the appropriate context

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            // Get the entered username and password
            val etUsername = findViewById<EditText>(R.id.inputUsername)
            val etPassword = findViewById<EditText>(R.id.inputPassword)
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            // Check if any of the fields are empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Perform database login check
                val isLoginSuccessful = dbHelper.checkUser(username, password)

                if (isLoginSuccessful) {
                    // Login successful, proceed to the main activity
                    val intent = Intent(this, SneakerStoreActivity::class.java)
                    val userId = dbHelper.getUserId(username, password)
                    val bundle = Bundle()
                    bundle.putString("id", userId.toString())
                    intent.putExtras(bundle)
                    startActivity(intent)
                } else {
                    // Login failed, display an error message
                    Toast.makeText(this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val btnSignup = findViewById<Button>(R.id.btnLoginToSignup)
        btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}
