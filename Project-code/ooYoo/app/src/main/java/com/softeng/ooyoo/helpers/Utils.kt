package com.softeng.ooyoo.helpers

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.travel.UserAndTravelEvent
import com.softeng.ooyoo.user.User
import com.ybs.countrypicker.CountryPicker
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList
import kotlin.math.abs

/**
 * This file contains functions used in many places of the project.
 * This is done for convenience and re-usability.
 */

const val THREE_DAYS_IN_MILLIS = 259200000

/**
 * Those function are used to easier show toasts.
 */
fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.longToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

/**
 * This function is used to convert a map representing a date to a string.
 */
fun dateMapToString(m: MutableMap<String, Int>) = m["Day"].toString() + "/" + m["Month"]?.plus(1).toString() + "/" + m["Year"].toString()

/**
 * This function is used to pick a date using a dialog.
 */
fun addDate(
    context: Context,
    startYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    startMonth: Int = Calendar.getInstance().get(Calendar.MONTH),
    startDay: Int = Calendar.getInstance().get(Calendar.DATE),
    onPick: (date: MutableMap<String, Int>) -> Unit){

    val date = mutableMapOf<String, Int>()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, day: Int ->
            date["Year"] = year
            date["Month"] = month
            date["Day"] = day
            onPick(date)
        },
        startYear,
        startMonth,
        startDay
    )
    datePickerDialog.show()

}

/**
 * This function is used to pick a place, specifically a country.
 */
fun addLocation(supportFragmentManager: FragmentManager, onPick: (country: String) -> Unit){
    val countryPicker = CountryPicker.newInstance("Select Country")
    countryPicker.setListener{ name: String, _: String, _: String, _: Int ->
        onPick(name)
        countryPicker.dismiss()
    }
    countryPicker.show(supportFragmentManager, "COUNTRY_PICKER")
}

/**
 * This function returns the difference between 2 dates in millis.
 */
fun dateDistance(date1: MutableMap<String, Int>, date2: MutableMap<String, Int>, absolute: Boolean = false): Long{
    val diff = dateMapToMillis(date2) - dateMapToMillis(date1)

    val dayDiff = TimeUnit.MILLISECONDS.toDays(abs(diff))

    return if(absolute){
        dayDiff
    }
    else{
        if(diff < 0){
            -1 * dayDiff
        }
        else{
            dayDiff
        }
    }
}

/**
 * This function check if one date is in the future.
 */
fun checkIfDateIsFuture(date: MutableMap<String, Int>): Boolean{
    val c = Calendar.getInstance()
    c.set(date["Year"] ?: 1900, date["Month"] ?: 0, date["Day"] ?: 1)
    return (System.currentTimeMillis() - c.timeInMillis) < 0
}

/**
 * This function converts a map representing a date to millis.
 */
fun dateMapToMillis(date: MutableMap<String, Int>): Long{
    val c = Calendar.getInstance()
    c.set(date["Year"] ?: 2020, date["Month"] ?: 0, date["Day"] ?: 1)
    return c.timeInMillis
}

/**
 * This gets 2 array lists, 1 of users and 1 of travelEvents,
 * and returns 1 array list of UserAndTravelEvent (merging the objects of the other 2)
 */
fun <T> mergeLists(users: ArrayList<User>, travelEventList: ArrayList<T>): ArrayList<UserAndTravelEvent>{
    val list = arrayListOf<UserAndTravelEvent>()

    val userMap = mutableMapOf<String, User>()
    val tripMap = mutableMapOf<String, T>()

    for (user in users){
        userMap[user.uid] = user
    }

    for (trip in travelEventList){
        tripMap[(trip as TravelEvent).uid ?: ""] = trip
    }

    val set = userMap.keys.intersect(tripMap.keys)

    for(item in set){
        if(userMap[item] != null && tripMap[item] != null) {
            list.add(
                UserAndTravelEvent(
                    userMap[item]!!,
                    (tripMap[item] as TravelEvent)
                )
            )
        }
    }

    return list
}