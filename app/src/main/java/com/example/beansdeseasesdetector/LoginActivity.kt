package com.example.beansdeseasesdetector

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beansdeseasesdetector.Model.Users
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.Objects

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var storageReference: StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val userEmail = findViewById<EditText>(R.id.et_username)
        val userPassword = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)
        val forgotPasswordTextView = findViewById<TextView>(R.id.tv_forgot_password)
        val signUpTextView = findViewById<TextView>(R.id.tv_sign_up)

        //businessProfile1 = findViewById(R.id.businessProfile1);
        storageReference = FirebaseStorage.getInstance().getReference("userProfile")
         // Initialize Firebase Auth

        mAuth = FirebaseAuth.getInstance()

        val firestore = FirebaseFirestore.getInstance()

        loginButton.setOnClickListener(View.OnClickListener { view: View? ->

        })

       /* loginButton.setOnClickListener {
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
        }*/

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
