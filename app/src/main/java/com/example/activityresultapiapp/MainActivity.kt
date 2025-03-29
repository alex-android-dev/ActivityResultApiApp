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

        val contractText = object : ActivityResultContract<Intent, String?>() {

            override fun createIntent(
                context: Context,
                input: Intent
            ): Intent {
                return input
            }

            override fun parseResult(
                resultCode: Int,
                intent: Intent?
            ): String? {

                if (resultCode == RESULT_OK) {
                    return intent?.getStringExtra(UsernameActivity.EXTRA_USERNAME)
                }

                return null
            }
        }

        val launcherText = registerForActivityResult(contractText) { text ->
            usernameTextView.text = text
        }

        val contractImage = object : ActivityResultContract<Intent, Uri?>() {
            override fun createIntent(
                context: Context,
                input: Intent
            ): Intent {
                return input
            }

            override fun parseResult(
                resultCode: Int,
                intent: Intent?
            ): Uri? {
                if (resultCode == RESULT_OK) {
                    return intent?.data
                }
                return null
            }
        }

        val launcherImage = registerForActivityResult(contractImage) { uri ->
            imageFromGalleryImageView.setImageURI(uri)
        }



        getUsernameButton.setOnClickListener {
            launcherText.launch(UsernameActivity.newIntent(this))
        }

        getImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type =
                    INTENT_TYPE_IMAGE
            }
            launcherImage.launch(intent)
        }
    }

    private fun initViews() {
        getUsernameButton = findViewById(R.id.get_username_button)
        usernameTextView = findViewById(R.id.username_textview)
        getImageButton = findViewById(R.id.get_image_button)
        imageFromGalleryImageView = findViewById(R.id.image_from_gallery_imageview)
    }

    companion object {
        private const val RC_USERNAME = 100
        private const val RC_IMAGE = 101
        private const val INTENT_TYPE_IMAGE = "image/*"

        fun log(text: String) {
            Log.d("MainActivity", text)
        }
    }
}