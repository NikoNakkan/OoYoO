package com.softeng.ooyoo.user

import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.chat.Chat
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.portfolio.PortfolioEntry
import com.softeng.ooyoo.trip.Trip
import java.util.*
import kotlin.collections.ArrayList

open class User(
    private val uid: String,
    private val username: String,
    private val email: String,
    private val fullName: String,
    private val phoneNumber: String,
    private val age: Date,
    private val livingIn: String,
    private val languages: ArrayList<String>,
    private val gender: String,
    private val interests: ArrayList<String>,
    private val userRating: ArrayList<Rating>,
    private val tripHistory: ArrayList<Trip>,
    private val hostHistory: ArrayList<Hosting>,
    private val carpoolingHistory: ArrayList<Carpooling>,
    private val portfolio: ArrayList<PortfolioEntry>,
    private val chats: ArrayList<Chat>)