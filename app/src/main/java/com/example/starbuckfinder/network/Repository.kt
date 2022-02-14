package com.example.starbuckfinder.network

class Repository constructor(private val retrofitService: RetrofitService) {

    //fun getNearbyStarbucks() = retrofitService.getNearbyStarbucks("cafe", "Starbucks","44.866105,-93.406417", "distance")
    fun getNearbyStarbucks() = retrofitService.getAllMovies()
}
