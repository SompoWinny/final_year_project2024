package com.example.beansdeseasesdetector
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ViewResultsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_result)

        val backToUploadButton = findViewById<Button>(R.id.backToUploadButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        backToUploadButton.setOnClickListener {
            navigateToUploadPicture()
        }

        logoutButton.setOnClickListener {
            navigateToMainActivity()
        }
    }

    private fun navigateToUploadPicture() {
        val intent = Intent(this, UploadPictureActivity::class.java)
        startActivity(intent)
        finish() // Optional: Close this activity from back stack
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity() // Close all activities in the stack
    }
}
