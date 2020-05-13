package com.softeng.ooyoo.carpool

import android.os.Parcelable
import com.softeng.ooyoo.place.Place
import kotlinx.android.parcel.Parcelize

@Parcelize
class TripDetails(
    val dateTime: String = "",
    val start: Place = Place(),
    val destination: Place = Place(),
    val intermediateStops: ArrayList<Place> = arrayListOf()
): Parcelable