package com.softeng.ooyoo.place

import android.os.Parcelable
import com.softeng.ooyoo.portfolio.Article
import kotlinx.android.parcel.Parcelize
import com.softeng.ooyoo.helpers.Rating

/**
 * This class represents a place.
 * For the current version of the code we are only working with countries,
 * and we represent them using the "name" attribute.
 */
@Parcelize
class Place (
    var name: String="",
    private val rating: ArrayList<Rating> = arrayListOf(),
    private val longlat: String="",
    private val articles: ArrayList<Article> = arrayListOf()
): Parcelable