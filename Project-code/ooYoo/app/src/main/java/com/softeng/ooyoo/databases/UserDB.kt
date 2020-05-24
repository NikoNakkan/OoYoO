package com.softeng.ooyoo.databases

import android.media.Rating
import android.util.Log
import android.util.Rational
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.Article
import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.User

class UserDB: Database(USERS) {

    public fun getTripPlanList(uid: String): ArrayList<TripPlan>{

        return arrayListOf()
    }

    public fun getCarpoolingList(uid: String): ArrayList<Carpooling>{

        return arrayListOf()
    }

    public fun getHostingList(uid: String): ArrayList<Hosting>{

        return arrayListOf()
    }

    public fun deleteAccount(uid: String){

    }

    public fun saveChanges(uid: String, user: User){

    }

    public fun uploadTripOnDatabase(uid: String, tripPlan: TripPlan){

    }

    public fun uploadHostingOnDatabase(uid: String, hosting: Hosting){

    }

    public fun uploadCarpoolingOnDatabase(uid: String, carpooling: Carpooling){

    }

    public fun retrieveUsers(): ArrayList<User>{

        return arrayListOf()
    }

    public fun retrieveDrivers(): ArrayList<User>{

        return arrayListOf()
    }

    public fun uploadReviewOnDatabase(uid: String, rating: Rating){

    }

    public fun retrieveSearchedUsers(uids: ArrayList<String>, onSuccess: (ArrayList<User>) -> Unit, onFailure: (Boolean) -> Unit){
        val db = FirebaseFirestore.getInstance()

        if(uids.size == 0) {
            onFailure(true)
            return
        }

        db.collection(collection)
            .whereIn("uid", uids)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val users = arrayListOf<User>()
                for (document in querySnapshot){
                    users.add(document.toObject(User::class.java))
                }

                onSuccess(users)
            }
            .addOnFailureListener { e ->
                onFailure(false)
                Log.e(UserDB::class.java.simpleName, "There was an error.", e)
            }
    }

    public fun retrieveSearchedUser(uid: String): User{

        return User()
    }

    public fun saveReport(uid: String, reportedUid: String, reasonForReport: String){

    }

    public fun saveBlock(uid: String,blockedUid: String, reasonForBlock: String){

    }

    public fun storeTripPlan(uid: String, tripPlan: TripPlan){

    }

    public fun storeFile(uid: String, fileId: String){

    }

    public fun storeArticle(uid: String, article: Article){

    }

    public fun retrieveInfo(uid: String): ArrayList<String>{

        return arrayListOf()
    }

}