package com.example.starbuckfinder.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.starbuckfinder.model.Starbuck
import com.example.starbuckfinder.network.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StarbuckViewModel constructor(private val repository: Repository)  : ViewModel() {
    val sharedData =  MutableLiveData<Starbuck>()
    val movieList = MutableLiveData<List<Starbuck>>()
    val errorMessage = MutableLiveData<String>()

    fun getNearbyStarbucks() {

        val response = repository.getNearbyStarbucks()
        response.enqueue(object : Callback<List<Starbuck>> {
            override fun onResponse(call: Call<List<Starbuck>>, response: Response<List<Starbuck>>) {
                Log.d("Kajal","Response::::"+response.body());
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<List<Starbuck>>, t: Throwable) {
                Log.d("Kajal","Response::::"+t.message);
                errorMessage.postValue(t.message)
            }
        })

    }

    fun setData(input: Starbuck) {
        sharedData.value = input
        Log.d("Kajal","setdata :::"+input)
    }

    // function to get the changed data
    fun getData(): MutableLiveData<Starbuck> = sharedData
}