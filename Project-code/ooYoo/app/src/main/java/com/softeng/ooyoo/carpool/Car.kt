package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Car(
    private val type: String="",
    private val carUse: String="",
    private val carState: Boolean= false,
    private val carServiceDate: Long =0,
    private val safety: String="",
    private val carFeatures: CarFeatures = CarFeatures(),
    private val carSpecs: CarSpecs = CarSpecs()
): Parcelable