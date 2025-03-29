package com.example.activityresultapiapp

import android.app.ComponentCaller
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

        getUsernameButton.setOnClickListener {

            UsernameActivity.newIntent(this).apply {
                startActivityForResult(this, RC_USERNAME)
                // Тут указываем Request код, чтобы понимать какой объект вызывается, потому что таких методов будет несколько
                // По данному коду мы поймем какие данные нам вернул метод
                // Мы будем тут ожидать результат с кодом 100
            }

            //TODO get username
        }
        getImageButton.setOnClickListener {
            // Это неявный интент на открытие галереи
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*" // MIME types - указываем какой тип хотим, чтобы был в интенте
                startActivityForResult(this, RC_IMAGE)
            }
            //TODO get image
        }
    }

    // Метод, который позволяет получить данные из другой Активити
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        intent: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, intent, caller)

        if (resultCode == RESULT_OK) {
            when (requestCode) {
                RC_USERNAME -> {
                    val username = intent?.getStringExtra(UsernameActivity.EXTRA_USERNAME) ?: ""
                    usernameTextView.text = username
                }

                RC_IMAGE -> {
                    val imageUri = intent?.data
                    imageFromGalleryImageView.setImageURI(imageUri)
                }
            }
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
    }
}