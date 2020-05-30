package com.softeng.ooyoo.carpool

import android.os.Parcelable
import com.google.android.gms.common.util.PlatformVersion
import kotlinx.android.parcel.Parcelize
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates

/**
 * A class representing a carpooling travel event.
 */
@Parcelize
class Carpooling(
    override val uid: String?,
    override val place: Place,
    override val dates: Dates,
    private val driverId: String = "",
    private val registerDate: Long =0,
    private val car: Car = Car(),
    private val carTrip: CarTrip = CarTrip()
): TravelEvent(uid, place, dates), Parcelable