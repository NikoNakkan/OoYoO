package com.softeng.ooyoo.host

import android.os.Parcelable
import com.softeng.ooyoo.place.Place
import kotlinx.android.parcel.Parcelize

@Parcelize
class House(
    val ownerId: String = "",
    val place: Place = Place(),
    val photoLinks: ArrayList<String> = arrayListOf()
): Parcelable