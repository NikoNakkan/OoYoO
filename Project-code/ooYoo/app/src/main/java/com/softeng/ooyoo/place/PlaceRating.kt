package com.softeng.ooyoo.place

import android.media.Rating
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class PlaceRating (val ratings: ArrayList<Rating> = arrayListOf()): Parcelable