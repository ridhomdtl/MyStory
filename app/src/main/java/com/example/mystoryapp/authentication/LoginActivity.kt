package com.example.mystoryapp.authentication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mystoryapp.story.MainActivity
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.databinding.ActivityLoginBinding
import com.example.mystoryapp.userpreferences.UserPreferences
import com.example.mystoryapp.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferences = UserPreferences(this)
        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        supportActionBar?.hide()

        playLoginAnimation()

        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvIntentRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            Toast.makeText(applicationContext, "Go to login page", Toast.LENGTH_SHORT).show()
        }
    }

    private fun playLoginAnimation() {
        val welcome = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(400)
        val desc = ObjectAnimator.ofFloat(binding.tvLoginIntro, View.ALPHA, 1f).setDuration(400)
        val email = ObjectAnimator.ofFloat(binding.edLoginEmail, View.ALPHA, 1f).setDuration(400)
        val password = ObjectAnimator.ofFloat(binding.edLoginPassword, View.ALPHA, 1f).setDuration(400)
        val button = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(400)
        val bottom = ObjectAnimator.ofFloat(binding.llHaveAccount, View.ALPHA, 1f).setDuration(400)

        AnimatorSet().apply {
            playSequentially(welcome, desc, email, password, button, bottom)
            start()
        }
    }

    private fun login(){

        val emailcheck = binding.edLoginEmail.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(emailcheck).matches()){
            binding.edLoginEmail.requestFocus()
            return

        }else if (binding.edLoginPassword.text!!.length < 8){
            binding.edLoginPassword.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit().login(
            binding.edLoginEmail.text.toString(),
            binding.edLoginPassword.text.toString())
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val responseBody = response.body()

                    if(response.isSuccessful){

                        val loginResult = response.body()!!.loginResult

                        if(loginResult != null){
                            userPreferences.userLogin(
                                loginResult.token,
                                loginResult.userId,
                                loginResult.name
                            )
                        }

                        Toast.makeText(this@LoginActivity, "Login ${responseBody!!.message}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                    } else{
                        Toast.makeText(this@LoginActivity, "Wrong email or password", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                    Log.e(ContentValues.TAG, "onFailure: ${t.message}")
                }
            })
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        startActivity(Intent(this@LoginActivity, WelcomeActivity::class.java))
        finish()
    }

}