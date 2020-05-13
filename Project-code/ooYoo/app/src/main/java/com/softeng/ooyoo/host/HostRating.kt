package com.softeng.ooyoo.host

import android.media.Rating
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class HostRating(val ratings: ArrayList<Rating> = arrayListOf()): Parcelable