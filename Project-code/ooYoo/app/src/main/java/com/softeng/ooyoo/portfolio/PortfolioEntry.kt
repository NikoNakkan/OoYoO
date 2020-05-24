package com.softeng.ooyoo.portfolio

import android.os.Parcelable
import com.softeng.ooyoo.Article
import com.softeng.ooyoo.trip.TripPlan
import kotlinx.android.parcel.Parcelize

@Parcelize
class PortfolioEntry(
    private val tripPlan: TripPlan = TripPlan(),
    private val fileLinks: ArrayList<String> = arrayListOf(),
    private val articles: ArrayList<Article> = arrayListOf()
): Parcelable