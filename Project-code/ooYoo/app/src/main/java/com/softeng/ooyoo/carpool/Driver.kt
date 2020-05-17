package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.softeng.ooyoo.helpers.Rating
import java.sql.Timestamp

@Parcelize
class Driver(
    private val age: Int=0,
    private val gender: String="",
    private val insurance: Long =0,
    private val validLicense: Boolean=false,
    private val nationality: String="",
    private val medicalBackground: String="",
    private val driverRating: ArrayList<Rating> = arrayListOf(),
    private val driverExperience: DriverExperience = DriverExperience()
): Parcelable