package com.softeng.ooyoo.user

import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.helpers.Gender
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.travel.Traveling
import java.util.*
import kotlin.collections.ArrayList

open class User(
    val uid: String,
    val username: String,
    val email: String,
    val fullName: String,
    val phoneNumber: String,
    val age: Date,
    val livingIn: String,
    val languages: ArrayList<String>,
    val gender: Gender,
    val interests: ArrayList<String>,
    val rating: UserRating,
    val tripHistory: ArrayList<Traveling>,
    val hostHistory: ArrayList<Hosting>,
    val carpoolingHistory: ArrayList<Carpooling>)