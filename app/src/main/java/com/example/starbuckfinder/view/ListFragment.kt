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
            adapter.setMovieList(it)
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
}
