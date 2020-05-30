package com.softeng.ooyoo.databases

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.helpers.THREE_DAYS_IN_MILLIS
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.user.User

/**
 * This class provides communication with the database for anything related with the hostings.
 */
class HostingDB: Database(HOSTINGS) {

    private val dbCollection = FirebaseFirestore.getInstance().collection(this.collection)

    /**
     * This method registers a hosting in the database.
     */
    fun hostRegistration(context: Context, hosting: Hosting, onSuccess: (String) -> Unit) {
        dbCollection
        .add(hosting)
        .addOnSuccessListener {
            context.toast("Your registration was successful. Have a nice hosting!")
            onSuccess(it.id)
        }
        .addOnCanceledListener {
            context.toast("There was an error while registering your trip.")
            Log.e(
                TripPlansDB::class.java.simpleName,
                "There was an error while registering your Host."
            )
        }
    }

    /**
     * This method finds all the hostings with dates near to the given hosting.
     */
    fun findRelevantHostings(hosting: Hosting, onSuccess: (ArrayList<TravelEvent>, ArrayList<User>) -> Unit, onFailure: (Boolean) -> Unit){
        val uids = arrayListOf<String>()
        val hostings = arrayListOf<com.softeng.ooyoo.travel.TravelEvent>()
        val temp = arrayListOf<String>()

        val startDateQuery = dbCollection
            .whereEqualTo(FieldPath.of("place", "name"), hosting.place.name)
            .whereLessThan("startDateInMillis", hosting.startDateInMillis + THREE_DAYS_IN_MILLIS)
            .whereGreaterThan("startDateInMillis", hosting.startDateInMillis - THREE_DAYS_IN_MILLIS)

        val endDateQuery = dbCollection
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

    /**
     * This method returns all the hostings of a specific user.
     */
    fun getMyHostList(uid: String, onSuccess: (ArrayList<Hosting>) -> Unit, onFailure: () -> Unit){
        dbCollection
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val hostings = arrayListOf<Hosting>()

                for (document in querySnapshot.documents){
                    val hosting = document.toObject(Hosting::class.java)
                    if(hosting != null) {
                        hostings.add(hosting)
                    }
                }

                onSuccess(hostings)

            }
            .addOnFailureListener { e ->
                Log.e(TripPlansDB::class.java.simpleName, "Retrieving tripPlans failed.", e)
                onFailure()
            }
    }

}