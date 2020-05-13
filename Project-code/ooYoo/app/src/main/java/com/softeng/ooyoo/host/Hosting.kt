package com.softeng.ooyoo.host

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Hosting (
    val hostId: String = "",
    val hostRating: HostRating = HostRating(),
    val house: House = House()
): Parcelable