package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CarDetails(
    val carType: String = "",
    val carBrand: String = "",
    val carModel: String = "",
    val kilometres: Int = 0,
    val horsepower: Int = 0
): Parcelable