package com.example.mystoryapp.maps

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.ViewModelProvider
import com.example.mystoryapp.R
import com.example.mystoryapp.databinding.ActivityMapsBinding
import com.example.mystoryapp.story.ListStoryItem
import com.example.mystoryapp.story.MainActivity
import com.example.mystoryapp.userpreferences.UserPreferences
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Story Location"

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val jakarta = LatLng(-6.2, 106.8)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jakarta))
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        val mapViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MapsViewModel::class.java]

        val userPreferences = UserPreferences(this)

        mapViewModel.getLocation(userPreferences.userToken()!!)
        mapViewModel.storyList.observe(this) { stories ->
            setLocation(stories)
        }
    }

    @Suppress("DEPRECATION")
    private fun setLocation(storyList:List<ListStoryItem>){

        for (stories in storyList){
            val location = LatLng(stories.lat as Double,stories.lon as Double)
            mMap.addMarker(
                MarkerOptions()
                    .position(location)
                    .title(stories.name)
                    .icon(vectorToBitmap(R.drawable.ic_location_map, resources.getColor(R.color.gray_700)))
                    .snippet(storyDescription(stories.description))
            )

            boundsBuilder.include(location)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16f))
        }
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        if (vectorDrawable == null) {
            Log.e("BitmapHelper", "Resource not found")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun storyDescription(input: String):String{
        return if(input.length > 12){
            input.substring(0,11) + "...\""
        } else {
            input
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        startActivity(Intent(this@MapsActivity, MainActivity::class.java))
        finish()
    }
}