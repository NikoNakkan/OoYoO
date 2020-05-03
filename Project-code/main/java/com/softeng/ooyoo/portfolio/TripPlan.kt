package com.softeng.ooyoo.portfolio

import com.softeng.ooyoo.Article
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates

class TripPlan(val dates: Dates, val place: Place, val fileLinks: ArrayList<String>, val articles: ArrayList<Article>) {
}