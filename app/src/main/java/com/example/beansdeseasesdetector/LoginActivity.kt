package com.example.beansdeseasesdetector

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.et_username)
        val passwordEditText = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val forgotPasswordTextView = findViewById<TextView>(R.id.tv_forgot_password)
        val signUpTextView = findViewById<TextView>(R.id.tv_sign_up)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Perform login action (e.g., authenticate with backend)
            if (username == "example@email.com" && password == "password") {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                // Navigate to UploadPictureActivity
                val intent = Intent(this, UploadPictureActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        forgotPasswordTextView.setOnClickListener {
            // Navigate to ForgotPasswordActivity
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        signUpTextView.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
