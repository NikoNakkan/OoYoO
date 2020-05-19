package com.softeng.ooyoo.databases

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.trip.TripPlan

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


}