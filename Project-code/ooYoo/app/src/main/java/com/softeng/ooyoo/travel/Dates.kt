package com.softeng.ooyoo.travel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Dates (
    var startDate: MutableMap<String, Int> = mutableMapOf(),
    var endDate: MutableMap<String, Int> = mutableMapOf()
): Parcelable