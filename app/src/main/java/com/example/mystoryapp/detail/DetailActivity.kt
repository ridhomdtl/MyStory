package com.example.mystoryapp.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.databinding.ActivityDetailBinding
import com.example.mystoryapp.userpreferences.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferences = UserPreferences(this)
        binding = ActivityDetailBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)
        supportActionBar?.hide()

        getDetailStory(intent.getStringExtra("id")!!)
    }

    private fun getDetailStory(id: String){
        val token = userPreferences.userToken()!!
        val client = ApiConfig.instanceRetrofitWithToken(token).getDetailStory(id)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        val story = responseBody.story
                        detailStory(story)
                    }
                } else {
                    val toast = Toast.makeText(applicationContext,"Can Not Find Story", Toast.LENGTH_LONG)
                    toast.show()
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                val message = t.message
                val toast = Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT)
                toast.show()
            }

        })

    }

    private fun detailStory(query: Story){
        binding.ivStoryImage.load(query.photoUrl)
        binding.tvStoryName.text = query.name
        binding.tvStoryDesc.text = query.description

    }

}