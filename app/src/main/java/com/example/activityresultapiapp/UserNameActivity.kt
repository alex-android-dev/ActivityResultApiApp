package com.example.activityresultapiapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class UsernameActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var saveUsernameButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_username)
        initViews()

        saveUsernameButton.setOnClickListener {
            val username = usernameEditText.text.trim().toString()
            saveUsername(username)
            finish()
        }
    }

    private fun saveUsername(username: String) {
        Intent().apply {
            putExtra(EXTRA_USERNAME, username)
            setResult(RESULT_OK, this)
        }
        // Если у нас все пошло окей, то возвращаем Окей
        // Возвращаем тут интент с параметрами
    }

    private fun initViews() {
        usernameEditText = findViewById(R.id.username_edittext)
        saveUsernameButton = findViewById(R.id.save_username_button)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, UsernameActivity::class.java)

        const val EXTRA_USERNAME = "username"
    }

}