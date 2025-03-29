package com.example.activityresultapiapp

import android.app.ComponentCaller
import android.content.Context
import android.content.Intent
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


        // Это контракт. Говорит о том, что для запуска UsernameActivity нам понадобится Intent
        // Этот интент мы передадим, когда будем запускать задачу на выполнение
        // Также говорим, что от запускаемой Активити нам понадобится Строка String
        val contract = object : ActivityResultContract<Intent, String?>() {

            // Система запустит Активити и будет ожидать результата
            override fun createIntent(
                context: Context,
                input: Intent
            ): Intent {
                // Создаём Intent для получения результата
                log("create intent: ${input.type}")
                if (input.type == INTENT_TYPE_IMAGE) {
                    return input
                }
                    return input
            }

            // В случае когда прилетит результат будет вызван данный метод
            override fun parseResult(
                resultCode: Int,
                intent: Intent?
            ): String? {
                // Когда задача будет выполнена, то сюда прилетит resultCode и intent с данными

                // todo Проблема в том, что сюда уже приходит null объект
                log("parse intent: ${intent?.type}")

                if (resultCode == RESULT_OK) {
                    val username =
                        intent?.getStringExtra(UsernameActivity.EXTRA_USERNAME) ?: ""
                    return username
                }

                // Если resultCode не ОК
                return null

            }
        }

        // Создаём лончер, где мы подписываемся на задачу и когда она будет выполнена, то сработает коллбэк
        // Передаём в него наш контракт и создаём коллбэк, Сюда прилетит строка
        val launcher = registerForActivityResult(contract) { it ->
            log("contract value: $it")
            if (!it.isNullOrBlank()) { // Если строка не пустая и не равна null
                usernameTextView.text = it
            }
        }



        getUsernameButton.setOnClickListener {
            launcher.launch(UsernameActivity.newIntent(this))
        }

        getImageButton.setOnClickListener {
            // Это неявный интент на открытие галереи
            val intentForImage = Intent(Intent.ACTION_PICK).apply {
                type =
                    INTENT_TYPE_IMAGE // MIME types - указываем какой тип хотим, чтобы был в интенте
            }

            launcher.launch(intentForImage)

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