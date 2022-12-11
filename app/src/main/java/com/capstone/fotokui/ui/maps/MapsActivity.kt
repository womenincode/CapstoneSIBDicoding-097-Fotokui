package com.capstone.fotokui.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.capstone.fotokui.R
import com.capstone.fotokui.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupFullScreen()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setupListener()
        getCurrentLocation()
        addMarks()
    }

    private fun setupListener() {
        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    @Suppress("DEPRECATION")
    private fun setupFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> getCurrentLocation()
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> getCurrentLocation()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getCurrentLocation() {
        val isPermissionGranted =
            checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (isPermissionGranted) {
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                val currentLocation = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
            }
        } else {
            requestLocationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun addMarks() {
        val nearbyPhotographers = listOf(
            LatLng(-7.412694, 109.299787),
            LatLng( -7.422082, 109.282035),
            LatLng(-7.418094, 109.271649),
            LatLng(-7.399631, 109.333118),
            LatLng(-7.400473, 109.241174),
            LatLng( -7.389866, 109.310406)
        )
        nearbyPhotographers.forEach { location ->
            mMap.addMarker(MarkerOptions().position(location))
        }
    }
}