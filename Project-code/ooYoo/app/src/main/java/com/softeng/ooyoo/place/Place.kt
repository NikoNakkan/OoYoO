package com.softeng.ooyoo.place

import android.os.Parcelable
import com.softeng.ooyoo.Article
import kotlinx.android.parcel.Parcelize
import com.softeng.ooyoo.helpers.Rating
@Parcelize
class Place (private val name: String="",
             private val rating: ArrayList<Rating>=arrayListOf(),
private val longlat: String="",
private val articles: ArrayList<Article>=arrayListOf())