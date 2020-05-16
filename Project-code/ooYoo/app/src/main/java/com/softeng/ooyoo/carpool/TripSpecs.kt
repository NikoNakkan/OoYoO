package com.softeng.ooyoo.carpool

import com.softeng.ooyoo.place.Place

class TripSpecs(private val start: String="",
                private val intermediateStops: ArrayList<String>= arrayListOf(),
                private val destination: String="",
                private val numOfStops: Int=0,
                private val time: String="")