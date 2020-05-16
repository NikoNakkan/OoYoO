package com.softeng.ooyoo.user

import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.chat.Chat
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.portfolio.PortfolioEntry
import com.softeng.ooyoo.trip.Trip
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
open class User(
private val uid: String="",
private val username: String="",
private val email: String="",
private val fullName: String="",
private val phoneNumber: String="",
private val age: Calendar= Calendar.getInstance(),
private val livingIn: String="",
private val languages: ArrayList<String>=arrayListOf(),
private val gender: String="",
private val interests: ArrayList<String>=arrayListOf(),
private val userRating: ArrayList<Rating>=arrayListOf(),
private val tripHistory: ArrayList<Trip>=arrayListOf(),
private val hostHistory: ArrayList<Hosting>=arrayListOf(),
private val carpoolingHistory: ArrayList<Carpooling>=arrayListOf(),
private val portfolio: ArrayList<PortfolioEntry>=arrayListOf(),
private val chats: ArrayList<Chat>=arrayListOf())