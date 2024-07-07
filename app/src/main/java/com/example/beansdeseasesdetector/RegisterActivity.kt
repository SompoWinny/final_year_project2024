package com.example.beansdeseasesdetector

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var roleEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        roleEditText = findViewById(R.id.roleEditText)

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val role = roleEditText.text.toString()
            val isSuccess = registerUser(email, password, role)

            if (isSuccess) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                navigateToLogin()
            } else {
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String, role: String): Boolean {
        val dbHelper = DatabaseHelper(this)
        return dbHelper.registerUser(email, password, role)
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close this activity from back stack
    }
}
