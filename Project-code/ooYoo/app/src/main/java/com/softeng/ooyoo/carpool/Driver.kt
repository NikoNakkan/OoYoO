package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Driver(
    val driverId: String = "",
    val driverExperience: DriverExperience = DriverExperience(),
    val driverRating: DriverRating = DriverRating()
):Parcelable