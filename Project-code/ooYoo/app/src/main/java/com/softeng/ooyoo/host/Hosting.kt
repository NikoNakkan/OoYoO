package com.softeng.ooyoo.host

import com.softeng.ooyoo.travel.TravelExperience
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates

class Hosting (private val place: Place, private val dates: Dates, private val hostId: String, private val hostRating: ArrayList<Rating>, private val housePhotosLinks: ArrayList<String>): TravelExperience(place, dates)