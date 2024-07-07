package com.example.beansdeseasesdetector

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class UploadPictureActivity : AppCompatActivity() {

    private lateinit var capturedImageView: ImageView
    private lateinit var currentPhotoPath: String

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            val file = File(currentPhotoPath)
            val uri = Uri.fromFile(file)
            displayCapturedImage(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_picture)

        val selectPictureButton = findViewById<Button>(R.id.selectPictureButton)
        val uploadPictureButton = findViewById<Button>(R.id.uploadPictureButton)
        capturedImageView = findViewById(R.id.capturedImageView)

        selectPictureButton.setOnClickListener {
            if (checkCameraPermissions()) {
                captureImageFromCamera()
            } else {
                requestCameraPermissions()
            }
        }

        uploadPictureButton.setOnClickListener {
            // Handle upload picture logic
            // For demo purposes, assume picture is uploaded and move to view results
            navigateToViewResults()
        }
    }

    private fun checkCameraPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_PERMISSION
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImageFromCamera()
            }
        }
    }

    private fun captureImageFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            // Create the file where the photo should go
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                // Error occurred while creating the File
                null
            }
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.example.beansdeseasesdetector.fileprovider",
                    it
                )
                takePictureLauncher.launch(photoURI)
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun displayCapturedImage(uri: Uri) {
        capturedImageView.setImageURI(uri)
        capturedImageView.visibility = ImageView.VISIBLE
        findViewById<Button>(R.id.uploadPictureButton).visibility = Button.VISIBLE
    }

    private fun navigateToViewResults() {
        val intent = Intent(this, ViewResultsActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 101
    }
}
