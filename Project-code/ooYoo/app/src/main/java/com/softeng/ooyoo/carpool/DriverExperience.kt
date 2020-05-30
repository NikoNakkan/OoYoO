package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

/**
 * A class containing information for the experience of a driver.
 */
@Parcelize
class DriverExperience(
    val experienceYears: Int = 0,
    val licenceDate: MutableMap<String,Int> = mutableMapOf(),
    val numCarUse: String = "",
    val drivingPlaceExperience: String = "",
    val skills: String = ""
): Parcelable