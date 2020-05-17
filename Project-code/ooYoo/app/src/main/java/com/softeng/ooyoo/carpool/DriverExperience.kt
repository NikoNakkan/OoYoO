package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.sql.Timestamp

@Parcelize
class DriverExperience(
    private val experienceYears: Int=0,
    private val licenceDate: Long =0,
    private val numCarUse: String="",
    private val drivingPlaceExperience: String="",
    private val skills: String=""
): Parcelable