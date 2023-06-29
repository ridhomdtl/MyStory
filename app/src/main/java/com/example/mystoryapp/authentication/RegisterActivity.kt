package com.example.mystoryapp.authentication

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.databinding.ActivityRegisterBinding
import com.example.mystoryapp.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        supportActionBar?.hide()

        playRegisterAnimation()

        binding.btnRegister.setOnClickListener {
            register()
        }

        binding.tvIntentLogin.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            Toast.makeText(applicationContext, "Go to login page", Toast.LENGTH_SHORT).show()
        }
    }

    private fun playRegisterAnimation() {
        val welcome = ObjectAnimator.ofFloat(binding.tvWelcome, View.ALPHA, 1f).setDuration(350)
        val desc = ObjectAnimator.ofFloat(binding.tvRegIntro, View.ALPHA, 1f).setDuration(350)
        val name = ObjectAnimator.ofFloat(binding.edRegisterName, View.ALPHA, 1f).setDuration(350)
        val email = ObjectAnimator.ofFloat(binding.edRegisterEmail, View.ALPHA, 1f).setDuration(350)
        val password = ObjectAnimator.ofFloat(binding.edRegisterPassword, View.ALPHA, 1f).setDuration(350)
        val button = ObjectAnimator.ofFloat(binding.btnRegister, View.ALPHA, 1f).setDuration(350)
        val bottom = ObjectAnimator.ofFloat(binding.llHaveAccount, View.ALPHA, 1f).setDuration(350)

        AnimatorSet().apply {
            playSequentially(welcome, desc, name, email, password, button, bottom)
            start()
        }
    }

    private fun register() {

        val emailcheck = binding.edRegisterEmail.text.toString()

        if(binding.edRegisterName.text!!.isEmpty()){
            binding.edRegisterName.requestFocus()
            return

        }else if(!Patterns.EMAIL_ADDRESS.matcher(emailcheck).matches()){
            binding.edRegisterEmail.requestFocus()
            return

        }else if (binding.edRegisterPassword.text!!.length < 8){
            binding.edRegisterPassword.requestFocus()
            return
        }

        ApiConfig.instanceRetrofit().register(
            binding.edRegisterName.text.toString(),
            binding.edRegisterEmail.text.toString(),
            binding.edRegisterPassword.text.toString())
            .enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    val responseBody = response.body()

                    if(response.isSuccessful){
                        Toast.makeText(this@RegisterActivity, responseBody!!.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))

                    } else{
                        Toast.makeText(this@RegisterActivity, responseBody!!.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(
                    call: Call<RegisterResponse>,
                    t: Throwable
                ) {
                    Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })

    }

    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        startActivity(Intent(this@RegisterActivity, WelcomeActivity::class.java))
        finish()
    }

}