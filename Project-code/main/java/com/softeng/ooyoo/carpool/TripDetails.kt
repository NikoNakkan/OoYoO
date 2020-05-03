package com.softeng.ooyoo.carpool

import com.softeng.ooyoo.place.Place

class TripDetails(val dateTime: String, val start: Place, val destination: Place, val intermediateStops: ArrayList<Place>)