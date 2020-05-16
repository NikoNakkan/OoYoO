package com.softeng.ooyoo.travel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Dates (private val startDate: Date=1/1/2000,
             private val endDate: Date=1/1/2000)