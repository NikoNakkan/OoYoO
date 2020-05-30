package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * The features of the car
 */
@Parcelize
class CarFeatures(
    private val brandName: String="",
    private val carVersion: Int=0,
    private val reservedKms: Int=0,
    private val producedDate: Int=0,
    private val ccs: Int=0,
    private val horsePower: Int=0,
    private val legalPapers: Boolean=false
): Parcelable