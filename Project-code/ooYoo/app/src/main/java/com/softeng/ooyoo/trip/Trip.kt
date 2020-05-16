package com.softeng.ooyoo.trip

import com.softeng.ooyoo.travel.TravelExperience
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.user.User
import com.softeng.ooyoo.travel.Dates

class Trip(private val place: Place,
           private val dates: Dates,
           private val buddies: ArrayList<User>=arrayListOf()): TravelExperience(place, dates)