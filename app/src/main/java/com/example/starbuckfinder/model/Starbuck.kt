package com.example.starbuckfinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Starbuck(val name: String, val imageUrl: String, val category: String, val desc: String, var lat: Double, var lon: Double): Parcelable