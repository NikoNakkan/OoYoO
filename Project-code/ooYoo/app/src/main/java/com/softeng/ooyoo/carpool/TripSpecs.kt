package com.softeng.ooyoo.carpool

import android.os.Parcelable
import com.softeng.ooyoo.place.Place
import kotlinx.android.parcel.Parcelize

/**
 * A class containing information about the carpooling trip.
 */
@Parcelize
class TripSpecs(
    private val start: String = "",
    private val intermediateStops: ArrayList<String> = arrayListOf(),
    private val destination: String = "",
    private val numOfStops: Int = 0,
    private val time: String = ""
): Parcelable