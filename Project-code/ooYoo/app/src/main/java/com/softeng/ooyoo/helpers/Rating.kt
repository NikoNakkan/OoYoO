package com.softeng.ooyoo.helpers

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rating (
    private val stars: Int=0,
    private val comment: String=""
): Parcelable