package com.example.starbuckfinder.network

/*
Common repository for entire Project
We can specify all required services / network calls in this repository which can be accessed from anywhere in application
 */
class Repository constructor(private val retrofitService: RetrofitService) {

    //fun getNearbyStarbucks() = retrofitService.getNearbyStarbucks("cafe", "Starbucks","44.866105,-93.406417", "distance")
    fun getNearbyMovies() = retrofitService.getAllMovies()
}
