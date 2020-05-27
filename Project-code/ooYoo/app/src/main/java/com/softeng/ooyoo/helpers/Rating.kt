package com.softeng.ooyoo.helpers

import android.os.Parcelable
import com.softeng.ooyoo.user.User
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rating (
    val uid: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val stars: Double = 0.0,
    val comment: String = ""
): Parcelable