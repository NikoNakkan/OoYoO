package com.softeng.ooyoo.helpers

import android.os.Parcelable
import com.softeng.ooyoo.user.User
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rating (
    var uid: String = "",
    var name: String = "",
    val imageUrl: String = "",
    var stars: Double = 0.0,
    var comment: String = ""
): Parcelable