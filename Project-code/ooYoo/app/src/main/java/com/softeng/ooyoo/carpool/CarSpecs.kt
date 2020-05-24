package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CarSpecs(
    private val numSeats: Int = 0,
    private val numLuggage: Int = 0,
    private val foodDrinkAllowance: Boolean = false,
    private val allowPets: Boolean = false
): Parcelable