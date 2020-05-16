package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.softeng.ooyoo.travel.TravelExperience
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
import java.sql.Timestamp

@Parcelize
class Carpooling(private val place: Place,
                 private val dates: Dates,
                 private val driverId: String=0,
                 private val registerDate: Long =0,
                 private val car: Car,
                 private val driver: Driver,
                 private val carTrip: CarTrip): TravelExperience(place, dates)