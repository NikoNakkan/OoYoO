package com.softeng.ooyoo.databases

import android.content.Context
import android.util.Log
import android.util.Rational
import com.google.firebase.firestore.*
import com.softeng.ooyoo.Article
import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.chat.Message
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.mainScreens.MainActivity
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.User

class UserDB: Database(USERS) {

    private val db = FirebaseFirestore.getInstance()
    private val dbCollection = db.collection(this.collection)

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
        dbCollection
            .document(uid)
            .delete()
    }

    public fun saveChanges(uid: String, user: User){

    }

    public fun uploadTripOnDatabase(uid: String, tripId: String){
        dbCollection
            .document(uid)
            .update(
                "tripPlanHistory",
                FieldValue.arrayUnion(tripId)
            )
    }

    public fun uploadHostingOnDatabase(uid: String, hostingId: String){
        dbCollection
            .document(uid)
            .update(
                "hostHistory",
                FieldValue.arrayUnion(hostingId)
            )
    }

    public fun uploadCarpoolingOnDatabase(uid: String, carpoolingId: String){

    }

    public fun retrieveUsers(): ArrayList<User>{

        return arrayListOf()
    }

    public fun retrieveDrivers(): ArrayList<User>{

        return arrayListOf()
    }

    public fun uploadReviewOnDatabase(uid: String, rating: Rating){
        dbCollection
            .document(uid)
            .update(
                "userRating",
                FieldValue.arrayUnion(rating)
            )
    }

    public fun retrieveSearchedUsers(uids: ArrayList<String>, onSuccess: (ArrayList<User>) -> Unit, onFailure: (Boolean) -> Unit){
        if(uids.size == 0) {
            onFailure(true)
            return
        }

        dbCollection
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

    public fun retrieveSearchedUser(uid: String, onSuccess: (ArrayList<User>) -> Unit, onFailure: (Boolean) -> Unit){
        retrieveSearchedUsers(arrayListOf(uid), onSuccess, onFailure)
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

    public fun userListener(uid: String, onSuccess: (User?) -> Unit){
        dbCollection
            .document(uid)
            .addSnapshotListener { snapshot: DocumentSnapshot?, e: FirebaseFirestoreException? ->
                if (e != null || snapshot == null) {
                    Log.w(MainActivity::class.java.simpleName, "Listen failed.", e)
                    return@addSnapshotListener
                }

                val user = snapshot.toObject(User::class.java)
                onSuccess(user)
            }
    }

    public fun updateChat(uid: String, message: Message){
//        dbCollection
//            .document(uid)
//            .update(
//                FieldPath.of("chats", )
//            )
    }
}