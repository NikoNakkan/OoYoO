package com.softeng.ooyoo.helpers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rating (val stars: Int = 0, val comment: String = ""): Parcelable