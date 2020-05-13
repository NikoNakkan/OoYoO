package com.softeng.ooyoo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Article (
    val id: String = "",
    val title: String = "",
    val body: String = "",
    val authorId: String = "",
    val likes: Int = 0,
    val comments: ArrayList<String> = arrayListOf()
):Parcelable