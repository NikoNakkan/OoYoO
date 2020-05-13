package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CarSpecs(val numberOfSeats: Int = 0, val numberOfLuggage: Int = 0): Parcelable