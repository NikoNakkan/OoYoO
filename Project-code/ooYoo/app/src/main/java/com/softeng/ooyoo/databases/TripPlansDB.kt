package com.softeng.ooyoo.databases

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.helpers.THREE_DAYS_IN_MILLIS
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.User

const val TRAVELERS_EXTRA_NAME = "travelers"
const val TRIPS_EXTRA_NAME = "trips"
const val HOSTS_EXTRA_NAME = "hosts"
const val HOSTINGS_EXTRA_NAME = "hostings"

class TripPlansDB: Database(TRIP_PLANS){



    fun tripRegistration(context: Context, tripPlan: TripPlan, onSuccess: (String) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection(this.collection)
            .add(tripPlan)
            .addOnSuccessListener {
                context.toast("Your registration was successful. Have a nice trip!")
                onSuccess(it.id)
            }
            .addOnCanceledListener {
                context.toast("There was an error while registering your trip.")
                Log.e(
                    TripPlansDB::class.java.simpleName,
                    "There was an error while registering your trip."
                )
            }
    }

    fun findRelevantTripPlans(tripPlan: TripPlan, onSuccess: (ArrayList<TravelEvent>, ArrayList<User>) -> Unit, onFailure: (Boolean) -> Unit){
        val db = FirebaseFirestore.getInstance()
        val uids = arrayListOf<String>()
        val tripPlans = arrayListOf<com.softeng.ooyoo.travel.TravelEvent>()
        val temp = arrayListOf<String>()

        val startDateQuery = db.collection(this.collection)
            .whereEqualTo(FieldPath.of("place", "name"), tripPlan.place.name)
            .whereLessThan("startDateInMillis", tripPlan.startDateInMillis + THREE_DAYS_IN_MILLIS)
            .whereGreaterThan("startDateInMillis", tripPlan.startDateInMillis - THREE_DAYS_IN_MILLIS)

        val endDateQuery = db.collection(this.collection)
            .whereEqualTo(FieldPath.of("place", "name"), tripPlan.place.name)
            .whereLessThan("endDateInMillis", tripPlan.endDateInMillis + THREE_DAYS_IN_MILLIS)
            .whereGreaterThan("endDateInMillis", tripPlan.endDateInMillis - THREE_DAYS_IN_MILLIS)


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

                        val userDB = UserDB()
                        userDB.retrieveSearchedUsers(
                            uids,
                            onSuccess = { travelers ->
                                onSuccess(tripPlans, travelers)
                            },
                            onFailure = onFailure
                        )

                        Log.d(TripPlansDB::class.java.simpleName, "Successful data retrieval.")
                    }
                    .addOnFailureListener { e ->
                        onFailure(false)
                        Log.e(TripPlansDB::class.java.simpleName, "There was an error.", e)
                    }

            }
            .addOnFailureListener { e ->
                onFailure(false)
                Log.e(TripPlansDB::class.java.simpleName, "There was an error.", e)
            }
    }

}