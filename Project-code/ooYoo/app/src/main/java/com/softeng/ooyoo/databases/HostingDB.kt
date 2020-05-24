package com.softeng.ooyoo.databases

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.helpers.THREE_DAYS_IN_MILLIS
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.user.User

class HostingDB: Database(HOSTINGS) {

    fun hostRegistration(hosting: Hosting) {

    }

    fun findRelevantHostings(hosting: Hosting, onSuccess: (ArrayList<TravelEvent>, ArrayList<User>) -> Unit, onFailure: (Boolean) -> Unit){
        val db = FirebaseFirestore.getInstance()
        val uids = arrayListOf<String>()
        val hostings = arrayListOf<com.softeng.ooyoo.travel.TravelEvent>()
        val temp = arrayListOf<String>()

        val startDateQuery = db.collection(this.collection)
            .whereEqualTo(FieldPath.of("place", "name"), hosting.place.name)
            .whereLessThan("startDateInMillis", hosting.startDateInMillis + THREE_DAYS_IN_MILLIS)
            .whereGreaterThan("startDateInMillis", hosting.startDateInMillis - THREE_DAYS_IN_MILLIS)

        val endDateQuery = db.collection(this.collection)
            .whereEqualTo(FieldPath.of("place", "name"), hosting.place.name)
            .whereLessThan("endDateInMillis", hosting.endDateInMillis + THREE_DAYS_IN_MILLIS)
            .whereGreaterThan("endDateInMillis", hosting.endDateInMillis - THREE_DAYS_IN_MILLIS)


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
                                hostings.add(document.toObject(Hosting::class.java))
                            }
                        }

                        val userDB = UserDB()
                        userDB.retrieveSearchedUsers(
                            uids,
                            onSuccess = { travelers ->
                                onSuccess(hostings, travelers)
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