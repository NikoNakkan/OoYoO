package com.softeng.ooyoo.portfolio

import android.os.Parcelable
import com.softeng.ooyoo.trip.TripPlan
import kotlinx.android.parcel.Parcelize

/**
 * This class represents a portfolio entry.
 */
@Parcelize
class PortfolioEntry(
    private val tripPlan: TripPlan = TripPlan(),
    private val fileLinks: ArrayList<String> = arrayListOf(),
    private val articles: ArrayList<Article> = arrayListOf()
): Parcelable