package com.example.starbuckfinder.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.example.starbuckfinder.R

import com.example.starbuckfinder.databinding.ActivityMainBinding


import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*

/*
MainActivity to handle all fragments and handle UI
 */
class MainActivity : AppCompatActivity()  {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var currentLocation: Location

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val permissionId = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FusedLocationClient to get current device location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocation()
    }

    // Checking if location service is enabled in device
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Checking if location permission is enabled for application
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // request location permission application
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }


    // Once the permission is enabled by user then access current device location
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }

    /*
    Check location permission . If enabled then detect currenty location otherwise request location permission
     */
    @SuppressLint("MissingPermission", "SetTextI18n")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location != null) {
                        currentLocation = location
                        val geocoder = Geocoder(this, Locale.getDefault())
                        val list: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        Log.d(TAG,"currentlatitude:::"+list[0].latitude)
                        Log.d(TAG,"currentlongitude:::"+list[0].longitude)
                        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
                        val listFragment = ListFragment()
                        val bundle = Bundle()
                        bundle.putParcelable("location", location)
                        listFragment.arguments = bundle
                        fragmentTransaction.apply {
                            add(R.id.fragment_container_view, listFragment)
                            commit()
                        }

                    }
                }
            } else {
                Toast.makeText(this, "Please turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

}