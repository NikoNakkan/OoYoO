package com.softeng.ooyoo.user

import android.os.Parcelable
import com.softeng.ooyoo.helpers.Rating
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserRating(val ratings: ArrayList<Rating> = arrayListOf()): Parcelable