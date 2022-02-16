package com.example.starbuckfinder.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starbuckfinder.model.Model
import com.example.starbuckfinder.network.Repository

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



/*
Common ViewModel for making  network calls and handle reponse as MutableLivedata
 */
class MovieViewModel constructor(private val repository: Repository)  : ViewModel() {
    val movieList = MutableLiveData<List<Model>>()
    val errorMessage = MutableLiveData<String>()
    val TAG: String = "ViewModel"

    fun getNearbyStarbucks() {

        val response = repository.getNearbyMovies()  // API call using retrofit and handle success and fail responses
        response.enqueue(object : Callback<List<Model>> {
            override fun onResponse(
                call: Call<List<Model>>,
                response: Response<List<Model>>
            ) {
                Log.d(TAG, "Response::::" + response.body());
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Model>>, t: Throwable) {
                Log.d(TAG, "Response::::" + t.message);
                errorMessage.postValue(t.message)
            }
        })
    }

    }


