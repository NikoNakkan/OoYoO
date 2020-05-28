package com.softeng.ooyoo.host

import android.os.Parcelable
import com.softeng.ooyoo.place.Place
import kotlinx.android.parcel.Parcelize

@Parcelize
class House(
    var place: Place = Place(),
    var size: Int = 0,
    var numOfRooms: Int = 0,
    var floor: Int = 0,
    var housePhotoLinks: ArrayList<String> = arrayListOf()
): Parcelable