package com.softeng.ooyoo.carpool

import android.media.Rating
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DriverRating(
    val ratings: ArrayList<Rating> = arrayListOf()
): Parcelable