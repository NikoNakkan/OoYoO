package com.softeng.ooyoo.host

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.softeng.ooyoo.travel.TravelExperience
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
@Parcelize


class Hosting (private val place: Place,
               private val dates: Dates,
               private val hostId: String="",
               private val hostRating: ArrayList<Rating>=arrayListOf(),
private val housePhotosLinks: ArrayList<String>=arrayListOf()): TravelExperience(place, dates)