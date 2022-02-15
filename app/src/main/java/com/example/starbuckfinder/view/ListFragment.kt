package com.example.starbuckfinder.view

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.starbuckfinder.R
import com.example.starbuckfinder.`interface`.OnItemClickListener
import com.example.starbuckfinder.adapter.StarbuckAdapter
import com.example.starbuckfinder.model.Starbuck
import com.example.starbuckfinder.network.Repository
import com.example.starbuckfinder.network.RetrofitService
import com.example.starbuckfinder.viewModel.StarbuckViewModel
import com.example.starbuckfinder.viewModel.ViewModelFactory
import java.util.*


class ListFragment : Fragment(), OnItemClickListener {


    lateinit var viewModel: StarbuckViewModel

    private val retrofitService = RetrofitService.getInstance()
    lateinit var adapter : StarbuckAdapter
    private val TAG = "ListFragment"

    lateinit var currentLocation: Location

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.layout_list_frag
            , container, false)

        if (getArguments() != null) {
            currentLocation = getArguments()?.getParcelable("location")!!
            Log.d("Kajal","currentlatitude:::"+currentLocation.latitude)
            Log.d("Kajal","currentlongitude:::"+currentLocation.longitude)
        }
        viewModel = ViewModelProvider(this, ViewModelFactory(Repository(retrofitService))).get(StarbuckViewModel::class.java)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        adapter = StarbuckAdapter {starBuck ->
            this.onItemClick(starBuck);
        }
        recyclerView.adapter = adapter


        viewModel.movieList.observe(this.requireActivity()!!, Observer {
            Log.d(TAG, "onCreate: $it")

                setGeoCoordinates(this.currentLocation, it)

        })

        viewModel.errorMessage.observe(this.requireActivity()!!, Observer {

        })
        viewModel.getNearbyStarbucks()


        return view
    }


    override fun onItemClick(model: Starbuck?) {
        Log.d("Kajal","indide onClick1111::::"+model)
        val fragmentTransaction: FragmentTransaction? = getActivity()?.getSupportFragmentManager()?.beginTransaction()
        val mapFragment = MapsFragment()
        val bundle = Bundle()
        bundle.putParcelable("selectedStarbuck", model)
        mapFragment.setArguments(bundle)
        fragmentTransaction?.apply {
            add(R.id.fragment_container_view, mapFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    protected fun getLocationInLatLngRad(
        radiusInMeters: Int,
        currentLocation: Location
    ): Location? {
        val x0: Double = currentLocation.getLongitude()
        val y0: Double = currentLocation.getLatitude()
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
        copy.setLatitude(foundLatitude)
        copy.setLongitude(foundLongitude)
        return copy
    }

    fun setGeoCoordinates(
                          currentLocation: Location ,resultsArray: List<Starbuck>
    ): ArrayList<Starbuck> {
        var resultList: ArrayList<Starbuck> = arrayListOf()
        for (i in 0 until resultsArray.size - 1) {
            try {

                val location: Location = this.getLocationInLatLngRad(20000, this.currentLocation)!!
                resultsArray[i].lat = location.latitude
                resultsArray[i].lon = location.longitude
                resultList.add(resultsArray[i])
            } catch (e: Exception) {
                Log.e(TAG, "parseCafeData Error: " + e.toString())
            }
        }
        Log.d("Kajal", "resultList: " + resultList)
        adapter.setMovieList(resultList)
        return resultList;
    }
}
