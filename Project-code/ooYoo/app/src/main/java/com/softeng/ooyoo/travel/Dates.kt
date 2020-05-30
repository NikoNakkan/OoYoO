package com.softeng.ooyoo.travel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * This class represents the start and end date of a travel event as Maps.
 */
@Parcelize
class Dates (
    var startDate: MutableMap<String, Int> = mutableMapOf(),
    var endDate: MutableMap<String, Int> = mutableMapOf()
): Parcelable