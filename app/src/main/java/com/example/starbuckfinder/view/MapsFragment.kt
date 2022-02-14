package com.example.starbuckfinder.view

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.starbuckfinder.model.Starbuck
import com.example.starbuckfinder.network.Repository
import com.example.starbuckfinder.network.RetrofitService
import com.example.starbuckfinder.viewModel.StarbuckViewModel
import com.example.starbuckfinder.viewModel.ViewModelFactory
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {
    private var mMap: MapView? = null
    lateinit var viewModel: StarbuckViewModel
    lateinit var selectedStarbuck: Starbuck
    private val retrofitService = RetrofitService.getInstance()

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        // val latLng: LatLng = LatLng(selectedStarbuck.latitude, selectedStarbuck.longitude)
        val marker: MarkerOptions = MarkerOptions().position(LatLng(37.4220012, -122.0840024)).title("Marker")
        googleMap.addMarker(marker)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(37.4220012, -122.0840024)))
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(com.example.starbuckfinder.R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(com.example.starbuckfinder.R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        if (getArguments() != null) {
             selectedStarbuck = getArguments()?.getParcelable("selectedStarbuck")!!
            Log.d("Kajal","selectedStarbuck:::"+selectedStarbuck)
        }

    }



    override fun onResume() {
        super.onResume()
        mMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMap?.onPause()
    }

    override fun onStart() {
        super.onStart()
        mMap?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMap?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMap?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMap?.onLowMemory()
    }

}