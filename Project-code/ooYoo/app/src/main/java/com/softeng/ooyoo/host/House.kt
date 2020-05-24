package com.softeng.ooyoo.host

import android.os.Parcelable
import com.softeng.ooyoo.place.Place
import kotlinx.android.parcel.Parcelize

@Parcelize
class House(
    val place: Place = Place(),
    val size: Int = 0,
    val numOfRooms: Int = 0,
    val floor: Int = 0,
    val housePhotoLinks: ArrayList<String> = arrayListOf()
): Parcelable