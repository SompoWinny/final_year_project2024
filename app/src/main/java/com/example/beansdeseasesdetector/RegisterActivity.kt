package com.example.beansdeseasesdetector

import DatabaseHelper
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beansdeseasesdetector.Model.AllUsers
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.StorageReference

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var roleEditText: EditText
    private var mAuth: FirebaseAuth? = null
    private var storageReference: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        roleEditText = findViewById(R.id.roleEditText)

        mAuth = FirebaseAuth.getInstance()

        val firestore = FirebaseFirestore.getInstance()


        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val email = emailEditText.text.toString().trim { it <= ' ' }
            val password = passwordEditText.text.toString().trim { it <= ' ' }
            val role = roleEditText.text.toString().trim { it <= ' ' }

            if (email.isEmpty()) {
                emailEditText.error = "Enter email"
                emailEditText.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Invalid email!"
                emailEditText.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                passwordEditText.error = "Enter password"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 8) {
                passwordEditText.error = "Eight characters required!"
                passwordEditText.requestFocus()
                return@setOnClickListener
            }

            val dialog = ProgressDialog(this@RegisterActivity)
            dialog.setMessage("Please wait...")
            dialog.show()

            FirebaseAuth.getInstance().fetchSignInMethodsForEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val signInMethods = task.result?.signInMethods
                        if (signInMethods != null && signInMethods.isNotEmpty()) {
                            // Email is already registered
                            dialog.dismiss()
                            Toast.makeText(this@RegisterActivity, "Email is already taken, try another email", Toast.LENGTH_SHORT).show()
                        } else {
                            // Email is not registered
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val user = FirebaseAuth.getInstance().currentUser
                                        if (user != null) {
                                            val dbUser = FirebaseFirestore.getInstance()
                                            val userId = user.uid
                                            // Get new FCM registration token (assuming you have FCM setup)
                                            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                                                if (task.isSuccessful) {

                                                    val token = task.result

                                                    val newUser = AllUsers(
                                                        role,
                                                        email,
                                                        password,
                                                        token,
                                                        userId
                                                    )

                                                    firestore.collection("UserDetails")
                                                        .document(userId)
                                                        .set(newUser)
                                                        .addOnSuccessListener {
                                                            Log.d("TAG", "User data uploaded to Firestore for user ID: $userId")
                                                            Toast.makeText(this@RegisterActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                                                            dialog.dismiss()
                                                        }
                                                        .addOnFailureListener { e ->
                                                            Log.e("TAG", "Error uploading user data for user ID $userId: ${e.message}")
                                                            Toast.makeText(this@RegisterActivity, "Failed to register", Toast.LENGTH_SHORT).show()
                                                            dialog.dismiss()
                                                        }
                                                } else {
                                                    Log.e("TAG", "Failed to get FCM token: ${task.exception?.message}")
                                                    Toast.makeText(this@RegisterActivity, "Failed to get FCM token", Toast.LENGTH_SHORT).show()
                                                    dialog.dismiss()
                                                }
                                            }
                                        }
                                    } else {
                                        Log.e("TAG", "Failed to create user: ${task.exception?.message}")
                                        Toast.makeText(this@RegisterActivity, "Failed to register, try again two", Toast.LENGTH_SHORT).show()
                                        dialog.dismiss()
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Log.e("TAG", "Failed to create user one: ${e.message}")
                                    Toast.makeText(this@RegisterActivity, "Failed to register, try again three", Toast.LENGTH_SHORT).show()
                                    dialog.dismiss()
                                }
                        }
                    } else {
                        val exception = task.exception
                        Log.e("TAG", "Failed to check email existence: ${exception?.message}")
                        Toast.makeText(this@RegisterActivity, "Failed to check email, please ensure you have strong internet", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                }
        }
    }


    private fun registerUser(email: String, password: String, role: String): Boolean {
        val dbHelper = DatabaseHelper(this)
        return dbHelper.registerUser(email, password, role)
    }

    /*private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close this activity from back stack
    }*/
}
