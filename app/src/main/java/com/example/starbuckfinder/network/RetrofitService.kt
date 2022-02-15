package com.example.starbuckfinder.network



import com.example.starbuckfinder.model.Model
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/*
Common service using retrofit for all network calls.
There is just 1 object that can be used all over the project for making api calls
 */
interface RetrofitService {

    // api end point
    @GET("movielist.json")
    fun getAllMovies() : Call<List<Model>>

    companion object {

        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {

            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                        .baseUrl("https://howtodoandroid.com/")
                        .addConverterFactory(GsonConverterFactory.create())  // Converter factory need to  convert JSON data (got from server) into java (model) objects (POJO)
                        .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}
