package com.example.starbuckfinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*
data class to define model/ POJO object retreived from server
 */
@Parcelize
data class Model(val name: String, val imageUrl: String, val category: String, val desc: String, var lat: Double, var lon: Double): Parcelable