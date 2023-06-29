package com.example.mystoryapp.welcome

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mystoryapp.authentication.LoginActivity
import com.example.mystoryapp.authentication.RegisterActivity
import com.example.mystoryapp.databinding.ActivityWelcomeBinding
import com.example.mystoryapp.story.MainActivity
import com.example.mystoryapp.userpreferences.UserPreferences

class WelcomeActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferences = UserPreferences(this)
        binding = ActivityWelcomeBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        supportActionBar?.hide()

        if(userPreferences.userStatus()){
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
        }

        binding.btnRegis.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, RegisterActivity::class.java))
            Toast.makeText(applicationContext, "Go to register page", Toast.LENGTH_SHORT).show()
        }

        binding.btnLog.setOnClickListener {
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
            Toast.makeText(applicationContext, "Go to login page", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        finishAffinity()
    }

}