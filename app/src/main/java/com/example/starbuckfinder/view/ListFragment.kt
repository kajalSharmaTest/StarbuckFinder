package com.example.starbuckfinder.view

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.starbuckfinder.R
import com.example.starbuckfinder.`interface`.OnItemClickListener
import com.example.starbuckfinder.adapter.ListAdapter
import com.example.starbuckfinder.model.Model
import com.example.starbuckfinder.network.Repository
import com.example.starbuckfinder.network.RetrofitService
import com.example.starbuckfinder.viewModel.MovieViewModel
import com.example.starbuckfinder.viewModel.ViewModelFactory
import java.util.*

/*
List Fragment to display all data received from server into list using recyclerView
 */
class ListFragment : Fragment(), OnItemClickListener {


    lateinit var viewModel: MovieViewModel
    lateinit var spinner: ProgressBar

    private val retrofitService = RetrofitService.getInstance()
    lateinit var adapter : ListAdapter
    private val TAG = "ListFragment"

    lateinit var currentLocation: Location

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.layout_list_frag
            , container, false)

        /*
        Retrieving current location detected in MainActivity
         */
        if (arguments != null) {
            currentLocation = requireArguments().getParcelable("location")!!
            Log.d(TAG,"currentlatitude:::"+currentLocation.latitude)
            Log.d(TAG,"currentlongitude:::"+currentLocation.longitude)
        }

        // viewModel that contain all data (server response)
        viewModel = ViewModelProvider(this, ViewModelFactory(Repository(retrofitService))).get(MovieViewModel::class.java)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
         spinner  = view.findViewById(R.id.progress_circular)

        // display spinner until we receive server response
        spinner.visibility = View.VISIBLE

        // instance of ListAdapter along with onItemClickListener to handle click event
        adapter = ListAdapter { starBuck ->
            this.onItemClick(starBuck)
        }
        recyclerView.adapter = adapter


        // Observer to handle success response changes in viewModel  as soon as we receive server response
        viewModel.movieList.observe(this.requireActivity(), Observer {
            Log.d(TAG, "onCreate: $it")
            // Once we receive server response adding random Geocordinates to each item in result
                setGeoCoordinates(it)
        })

        // Observer to handle failure response changes in viewModel  as soon as we receive api fail message
        viewModel.errorMessage.observe(this.requireActivity(), Observer {

        })

        // actual api call
        viewModel.getNearbyStarbucks()


        return view
    }

// OnItemClickListener implementation that triggers to launch MapFragment with the selected list item
    override fun onItemClick(model: Model?) {
        Log.d(TAG,"indide onItemClick::::"+model)
        val fragmentTransaction: FragmentTransaction? = activity?.supportFragmentManager?.beginTransaction()
        val mapFragment = MapsFragment()
        val bundle = Bundle()
        bundle.putParcelable("selectedMovie", model)
    mapFragment.arguments = bundle
        fragmentTransaction?.apply {
            add(R.id.fragment_container_view, mapFragment)
                .addToBackStack(null)
                .commit()
        }
    }


// Get random geoCordinates within 10 KM radius of current location
    protected fun getLocationInLatLngRad(
        radiusInMeters: Int,
        currentLocation: Location
    ): Location? {
        val x0: Double = currentLocation.longitude
        val y0: Double = currentLocation.latitude
        val random = Random()

        // Convert radius from meters to degrees.
        val radiusInDegrees = radiusInMeters / 111320f

        // Get a random distance and a random angle.
        val u: Double = random.nextDouble()
        val v: Double = random.nextDouble()
        val w = radiusInDegrees * Math.sqrt(u)
        val t = 2 * Math.PI * v
        // Get the x and y delta values.
        val x = w * Math.cos(t)
        val y = w * Math.sin(t)

        // Compensate the x value.
        val new_x = x / Math.cos(Math.toRadians(y0))
        val foundLatitude: Double
        val foundLongitude: Double
        foundLatitude = y0 + y
        foundLongitude = x0 + new_x
        val copy = Location(currentLocation)
    copy.latitude = foundLatitude
    copy.longitude = foundLongitude
        return copy
    }

    fun setGeoCoordinates(
                          resultsArray: List<Model>
    ): ArrayList<Model> {
        var resultList: ArrayList<Model> = arrayListOf()
        for (i in 0 until resultsArray.size - 1) {
            try {

                val location: Location = this.getLocationInLatLngRad(10000, this.currentLocation)!!
                resultsArray[i].lat = location.latitude
                resultsArray[i].lon = location.longitude
                resultList.add(resultsArray[i])
            } catch (e: Exception) {
                Log.e(TAG, "parseCafeData Error: " + e.toString())
            }
        }
        Log.d(TAG, "resultList: " + resultList)

        // Hide spinner and display recyclerView once we have the list ready along with geoCordinates set.
        spinner.visibility = View.GONE
        adapter.setMovieList(resultList)
        return resultList
    }
}
