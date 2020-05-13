package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Carpooling(
    val car: Car = Car(),
    val driver: Driver = Driver(),
    val trip: Trip = Trip()
):Parcelable