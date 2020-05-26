package com.softeng.ooyoo.host

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.helpers.dateMapToMillis
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates


@Parcelize
class Hosting (
    override val uid: String? = "",
    override val place: Place = Place(),
    override val dates: Dates = Dates(),
    private val hostId: String="",
    private val hostRating: ArrayList<Rating> = arrayListOf(),
    private val house: House = House(),
    var startDateInMillis: Long = 0,
    var endDateInMillis: Long = 0
): TravelEvent(uid, place, dates), Parcelable{

    init {
        startDateInMillis = dateMapToMillis(dates.startDate)
        endDateInMillis = dateMapToMillis(dates.endDate)
    }

}

