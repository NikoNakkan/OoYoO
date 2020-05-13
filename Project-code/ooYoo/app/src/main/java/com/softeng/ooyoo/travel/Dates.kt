package com.softeng.ooyoo.travel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Dates (val startDate: Date = Date(0), val endDate: Date = Date(0)): Parcelable