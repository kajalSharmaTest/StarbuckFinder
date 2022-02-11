package com.example.starbuckfinder.network

import com.example.starbuckfinder.model.Starbuck
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("api/place/nearbysearch/json?&key=AIzaSyArQqbk93voQ_cAhNtMSk5HkZXj8xcsZp8")
    fun getNearbyStarbucks(@Query("QUERY_TYPE") type: String?,@Query("KEYWORD") keyword: String?, @Query("LATITUDE_LONGITUDE") location: String?, @Query("RANK_BY") rankby: String): Call<List<Starbuck>>


    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://maps.googleapis.com/maps/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}