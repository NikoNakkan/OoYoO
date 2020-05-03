package com.softeng.ooyoo.carpool

import com.softeng.ooyoo.travel.TravelExperience
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
import java.sql.Timestamp

class Carpooling(private val place: Place, private val dates: Dates, private val driverId: String, private val registerDate: Timestamp, private val car: Car, private val driver: Driver, private val carTrip: CarTrip): TravelExperience(place, dates)