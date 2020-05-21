package com.softeng.ooyoo.databases

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.UsersListActivity
import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.helpers.THREE_DAYS_IN_MILLIS
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.trip.TripPlan

const val TRAVELERS_EXTRA_NAME = "travelers"
const val TRIPS_EXTRA_NAME = "trips"

class TravelEventDB: Database(TRAVEL_EVENTS){

    fun hostRegistration(uid: String, hosting: Hosting) {

    }

    fun tripRegistration(context: Context, tripPlan: TripPlan, onSuccess: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection(this.collection)
            .add(tripPlan)
            .addOnSuccessListener {
                context.toast("Your registration was successful. Have a nice trip!")
                onSuccess()
            }
            .addOnCanceledListener {
                context.toast("There was an error while registering your trip.")
                Log.e(
                    TravelEventDB::class.java.simpleName,
                    "There was an error while registering your trip."
                )
            }
    }

    fun carpoolerRegistration(uid: String, carpooling: Carpooling) {

    }

    fun findRelevantTripPlans(context: Context, tripPlan: TripPlan){
        val db = FirebaseFirestore.getInstance()
        val uids = arrayListOf<String>()
        val tripPlans = arrayListOf<TripPlan>()
        val temp = arrayListOf<String>()

        val startDateQuery = db.collection(this.collection)
            .whereEqualTo(FieldPath.of("place", "name"), tripPlan.place.name)
            .whereLessThan("startDateInMillis", tripPlan.startDateInMillis + THREE_DAYS_IN_MILLIS)
            .whereGreaterThan("startDateInMillis", tripPlan.startDateInMillis - THREE_DAYS_IN_MILLIS)

        val endDateQuery = db.collection(this.collection)
            .whereEqualTo(FieldPath.of("place", "name"), tripPlan.place.name)
            .whereLessThan("endDateInMillis", tripPlan.endDateInMillis + THREE_DAYS_IN_MILLIS)
            .whereGreaterThan("endDateInMillis", tripPlan.endDateInMillis - THREE_DAYS_IN_MILLIS)
//            .whereIn("uid", tripPlanList)


        startDateQuery
            .get()
            .addOnSuccessListener { querySnapshot ->
                for(document in querySnapshot.documents) {
                    temp.add(document["uid"].toString())
                }

                endDateQuery
                    .get()
                    .addOnSuccessListener { innerQuerySnapshot ->
                        for (document in innerQuerySnapshot){
                            if(document["uid"].toString() in temp){
                                uids.add(document["uid"].toString())
                                tripPlans.add(document.toObject(TripPlan::class.java))
                            }
                        }

                        if (uids.size == 0){
                            context.toast("There are no users for your destination.")
                            return@addOnSuccessListener
                        }

                        val userDB = UserDB()
                        userDB.retrieveSearchedUsers(uids){ travelers ->
                            val intent = Intent(context, UsersListActivity::class.java)
                            intent.putParcelableArrayListExtra(TRAVELERS_EXTRA_NAME, travelers)
                            intent.putParcelableArrayListExtra(TRIPS_EXTRA_NAME, tripPlans)
                            context.startActivity(intent)
                        }

                        Log.d(TravelEventDB::class.java.simpleName, "Successful data retrieval.")
                    }
                    .addOnFailureListener { e ->
                        Log.e(TravelEventDB::class.java.simpleName, "There was an error.", e)
                    }

            }
            .addOnFailureListener { e ->
                    Log.e(TravelEventDB::class.java.simpleName, "There was an error.", e)
            }
    }


}