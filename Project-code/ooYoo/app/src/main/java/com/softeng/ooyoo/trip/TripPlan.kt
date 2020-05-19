package com.softeng.ooyoo.trip

import android.os.Parcelable
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.user.User
import com.softeng.ooyoo.travel.Dates
import kotlinx.android.parcel.Parcelize

@Parcelize
class TripPlan(
    val uid: String = "",
    val place: Place = Place(),
    val dates: Dates = Dates(),
    val buddies: ArrayList<User> = arrayListOf()
): TravelEvent(place, dates), Parcelable