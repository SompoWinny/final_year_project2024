package com.example.beansdeseasesdetector

import DatabaseHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var newPasswordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        emailEditText = findViewById(R.id.emailEditText)
        newPasswordEditText = findViewById(R.id.newPasswordEditText)

        val updatePasswordButton = findViewById<Button>(R.id.updatePasswordButton)
        updatePasswordButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            updatePassword(this, email, newPassword)
        }
    }

    private fun updatePassword(context: Context, email: String, newPassword: String) {
        val dbHelper = DatabaseHelper(context)
        val isSuccess = dbHelper.updatePassword(email, newPassword)
        if (isSuccess) {
            Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show()
            navigateToLogin()
        } else {
            Toast.makeText(context, "Password update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close this activity from back stack
    }
}
