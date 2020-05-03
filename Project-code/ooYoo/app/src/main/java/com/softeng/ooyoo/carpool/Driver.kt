package com.softeng.ooyoo.carpool

import com.softeng.ooyoo.helpers.Rating
import java.sql.Timestamp

class Driver(private val age: Int, private val gender: String, private val insurance: Timestamp, private val validLicense: Boolean, private val nationality: String, private val medicalBackground: String, private val driverRating: ArrayList<Rating>, private val driverExperience: DriverExperience)
