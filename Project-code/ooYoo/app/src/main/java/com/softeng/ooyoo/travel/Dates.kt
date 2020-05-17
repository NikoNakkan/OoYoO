package com.softeng.ooyoo.travel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Dates (
    private val startDate: Calendar = Calendar.getInstance(),
    private val endDate: Calendar = Calendar.getInstance()
): Parcelable