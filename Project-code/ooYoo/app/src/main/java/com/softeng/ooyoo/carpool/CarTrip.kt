package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CarTrip(
    private val route: String="",
    private val estimatedHours: Int=0,
    private val safeTrip: Boolean=false,
    private val maxCarSpeed: Int=0,
    private val cabinConditions: String="",
    private val tripSpecs: TripSpecs = TripSpecs(),
    private val payment: Payment = Payment()
): Parcelable