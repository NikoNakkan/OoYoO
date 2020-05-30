package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * The specifications about this carpooling with for a specific car.
 */
@Parcelize
class CarSpecs(
    private val numSeats: Int = 0,
    private val numLuggage: Int = 0,
    private val foodDrinkAllowance: Boolean = false,
    private val allowPets: Boolean = false
): Parcelable