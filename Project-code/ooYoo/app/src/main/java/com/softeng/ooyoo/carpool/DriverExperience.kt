package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class DriverExperience(
    val yearOfLicense: Int = 0,
    val kilometres: Int = 0
): Parcelable