package com.softeng.ooyoo.place

import android.os.Parcelable
import com.softeng.ooyoo.Article
import kotlinx.android.parcel.Parcelize

@Parcelize
class Place (
    val name: String = "",
    val rating: PlaceRating = PlaceRating(),
    val longlat: String = "",
    val articles: ArrayList<Article> = arrayListOf()
): Parcelable