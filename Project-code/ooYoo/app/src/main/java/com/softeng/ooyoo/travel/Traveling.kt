package com.softeng.ooyoo.travel

import android.os.Parcelable
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.user.User
import kotlinx.android.parcel.Parcelize

@Parcelize
class Traveling(
    val place: Place = Place(),
    val dates: Dates = Dates(),
    val buddies: ArrayList<User> = arrayListOf()): Parcelable