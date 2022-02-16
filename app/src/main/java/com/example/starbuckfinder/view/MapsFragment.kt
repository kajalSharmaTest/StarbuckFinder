package com.example.starbuckfinder.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.starbuckfinder.model.Model
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/*
MapFragment to display selected list item on Map
 */
class MapsFragment : Fragment() {
    private var mMap: MapView? = null
    lateinit var selectedMovie: Model
    val ZOOM_LEVEL = 13f
    val TAG: String = "MapsFragment"

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * Adding selected list item as marker on map
     */
    private val callback = OnMapReadyCallback { googleMap ->
         val latLng: LatLng = LatLng(selectedMovie.lat, selectedMovie.lon)
        val marker: MarkerOptions = MarkerOptions().position(latLng).title(selectedMovie.name)
        googleMap.addMarker(marker)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL))
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

        // Retrieving selected list item from arguments passed from previous ListFragment
        if (getArguments() != null) {
            selectedMovie = getArguments()?.getParcelable("selectedMovie")!!
            Log.d(TAG,"selectedStarbuck:::"+selectedMovie)
        }

    }


// Handle Map lifecycle with Fragment lifecycle
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