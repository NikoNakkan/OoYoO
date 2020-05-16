package com.softeng.ooyoo.carpool

class CarTrip(private val route: String="",
              private val estimatedHours: Int=0,
              private val safeTrip: Boolean=false,
              private val maxCarSpeed: Int=0,
              private val cabinConditions: String="",
              private val tripSpecs: TripSpecs,
              private val payment: Payment)