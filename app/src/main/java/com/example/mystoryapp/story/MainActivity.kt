package com.example.mystoryapp.story

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystoryapp.R
import com.example.mystoryapp.addstory.AddStoryActivity
import com.example.mystoryapp.api.ApiConfig
import com.example.mystoryapp.databinding.ActivityMainBinding
import com.example.mystoryapp.maps.MapsActivity
import com.example.mystoryapp.userpreferences.UserPreferences
import com.example.mystoryapp.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userPreferences: UserPreferences
    private lateinit var binding : ActivityMainBinding
    private lateinit var stories : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userPreferences = UserPreferences(this)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        stories = binding.rvStories
        story(userPreferences.userToken()!!)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_story -> {
                startActivity(Intent(this, AddStoryActivity::class.java))
                finish()
            }
            R.id.maps_menu -> {
                startActivity(Intent(this, MapsActivity::class.java))
                finish()
            }
            R.id.logout_menu -> {
                userPreferences.userLogout()
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun story(token: String){
        val repository = StoriesRepository(ApiConfig.instanceRetrofitWithToken(token))
        val viewModel = MainViewModel(repository)

        binding.rvStories.layoutManager = LinearLayoutManager(this@MainActivity)

        val adapter = StoryAdapter()
        binding.rvStories.adapter = adapter

        viewModel.paging.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        finishAffinity()
    }
}