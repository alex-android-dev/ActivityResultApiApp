package com.example.activityresultapiapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var getUsernameButton: Button
    private lateinit var usernameTextView: TextView
    private lateinit var getImageButton: Button
    private lateinit var imageFromGalleryImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        val contractUserName = ActivityResultContracts.StartActivityForResult()
        val launcherUserName = registerForActivityResult(contractUserName) {
            if (it.resultCode == RESULT_OK) {
                usernameTextView.text = it.data?.getStringExtra(UsernameActivity.EXTRA_USERNAME)
            }
        }

        val galleryLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                try {
                    imageFromGalleryImageView.setImageURI(uri)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        getUsernameButton.setOnClickListener {
            launcherUserName.launch(UsernameActivity.newIntent(this))
        }

        getImageButton.setOnClickListener {
            galleryLauncher.launch(INTENT_TYPE_IMAGE)
        }
    }

    private fun initViews() {
        getUsernameButton = findViewById(R.id.get_username_button)
        usernameTextView = findViewById(R.id.username_textview)
        getImageButton = findViewById(R.id.get_image_button)
        imageFromGalleryImageView = findViewById(R.id.image_from_gallery_imageview)
    }

    companion object {
        private const val INTENT_TYPE_IMAGE = "image/*"

        private fun log(text: String) {
            Log.d("MainActivity", text)
        }
    }
}