package com.softeng.ooyoo.trip

import android.os.Parcelable
import com.softeng.ooyoo.helpers.dateMapToMillis
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.user.User
import com.softeng.ooyoo.travel.Dates
import kotlinx.android.parcel.Parcelize

@Parcelize
class TripPlan(
    override val uid: String? = "",
    override val place: Place = Place(),
    override val dates: Dates = Dates(),
    val buddies: ArrayList<User> = arrayListOf(),
    var startDateInMillis: Long = 0,
    var endDateInMillis: Long = 0
): TravelEvent(uid, place, dates), Parcelable{

    init {
        startDateInMillis = dateMapToMillis(dates.startDate)
        endDateInMillis = dateMapToMillis(dates.endDate)
    }

}