package com.softeng.ooyoo.user

import android.os.Parcelable
import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.helpers.Gender
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.travel.Traveling
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
open class User(
    var uid: String = "",
    val username: String = "",
    val email: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    var age: Calendar = Calendar.getInstance(),
    var livingIn: String = "",
    val languages: ArrayList<String> = arrayListOf(),
    var gender: String = "",
    val interests: ArrayList<String> = arrayListOf(),
    val rating: UserRating = UserRating(),
    val tripHistory: ArrayList<Traveling> = arrayListOf(),
    val hostHistory: ArrayList<Hosting> = arrayListOf(),
    val carpoolingHistory: ArrayList<Carpooling> = arrayListOf()): Parcelable