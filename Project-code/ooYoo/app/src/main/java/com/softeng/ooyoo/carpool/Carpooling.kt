package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates

@Parcelize
class Carpooling(
    private val place: Place,
    private val dates: Dates,
    private val driverId: String = "",
    private val registerDate: Long =0,
    private val car: Car = Car(),
    private val driver: Driver = Driver(),
    private val carTrip: CarTrip = CarTrip()
): TravelEvent(place, dates), Parcelable