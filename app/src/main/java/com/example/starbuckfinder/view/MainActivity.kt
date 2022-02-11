package com.example.starbuckfinder.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.starbuckfinder.R
import com.example.starbuckfinder.adapter.StarbuckAdapter
import com.example.starbuckfinder.databinding.ActivityMainBinding
import com.example.starbuckfinder.network.Repository
import com.example.starbuckfinder.network.RetrofitService
import com.example.starbuckfinder.viewModel.StarbuckViewModel
import com.example.starbuckfinder.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    lateinit var viewModel: StarbuckViewModel

    private val retrofitService = RetrofitService.getInstance()
    val adapter = StarbuckAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(Repository(retrofitService))).get(StarbuckViewModel::class.java)

        binding.recyclerview.adapter = adapter

        viewModel.movieList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setMovieList(it)
        })

        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getNearbyStarbucks()
    }
}