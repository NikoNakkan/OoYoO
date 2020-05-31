package com.softeng.ooyoo.databases

import android.util.Log
import com.google.firebase.firestore.*
import com.softeng.ooyoo.portfolio.Article
import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.mainScreens.MainActivity
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.User

/**
 * This class provides communication with the database for anything related with the users.
 */
class UserDB: Database(USERS) {

    private val dbCollection = FirebaseFirestore.getInstance().collection(this.collection)
    private lateinit var userListener: ListenerRegistration

    public fun getTripPlanList(uid: String): ArrayList<TripPlan>{
        TODO("Not implemented yet.")
    }

    public fun getCarpoolingList(uid: String): ArrayList<Carpooling>{
        TODO("Not implemented yet.")
    }

    public fun getHostingList(uid: String): ArrayList<Hosting>{
        TODO("Not implemented yet.")
    }

    /**
     * This method deletes a user account.
     */
    public fun deleteAccount(uid: String){
        dbCollection
            .document(uid)
            .delete()
    }

    public fun saveChanges(uid: String, user: User){
        TODO("Not implemented yet.")
    }

    /**
     * This method uploads a trip plan of a specific user.
     */
    public fun uploadTripOnDatabase(uid: String, tripId: String){
        dbCollection
            .document(uid)
            .update(
                "tripPlanHistory",
                FieldValue.arrayUnion(tripId)
            )
    }

    /**
     * This method uploads a hosting of a specific user.
     */
    public fun uploadHostingOnDatabase(uid: String, hostingId: String){
        dbCollection
            .document(uid)
            .update(
                "hostHistory",
                FieldValue.arrayUnion(hostingId)
            )
    }

    public fun uploadCarpoolingOnDatabase(uid: String, carpoolingId: String){
        TODO("Not implemented yet.")
    }

    public fun retrieveUsers(): ArrayList<User>{
        TODO("Not implemented yet.")
    }

    public fun retrieveDrivers(): ArrayList<User>{
        TODO("Not implemented yet.")
    }

    /**
     * This method uploads a user's review on the database.
     */
    public fun uploadReviewOnDatabase(uid: String, rating: Rating, onSuccess: () -> Unit, onFailure: () -> Unit){
        dbCollection
            .document(uid)
            .update(
                "userRating",
                FieldValue.arrayUnion(rating)
            )
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.e(UserDB::class.java.simpleName, "Error while uploading review", e)
                onFailure()
            }
    }

    /**
     * This method retrieves a list of users' data from a list of user ids.
     */
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

    /**
     * This method retrieves a user's data from a user id.
     */
    public fun retrieveSearchedUser(uid: String, onSuccess: (ArrayList<User>) -> Unit, onFailure: (Boolean) -> Unit){
        retrieveSearchedUsers(arrayListOf(uid), onSuccess, onFailure)
    }

    public fun saveReport(uid: String, reportedUid: String, reasonForReport: String){
        TODO("Not implemented yet.")
    }

    public fun saveBlock(uid: String,blockedUid: String, reasonForBlock: String){
        TODO("Not implemented yet.")
    }

    public fun storeTripPlan(uid: String, tripPlan: TripPlan){
        TODO("Not implemented yet.")
    }

    public fun storeFile(uid: String, fileId: String){
        TODO("Not implemented yet.")
    }

    public fun storeArticle(uid: String, article: Article){
        TODO("Not implemented yet.")
    }

    public fun retrieveInfo(uid: String): ArrayList<String>{
        TODO("Not implemented yet.")
    }

    /**
     * This method listens for changes in a specific user and
     */
    public fun addUserListener(uid: String, onSuccess: (User?) -> Unit){
        userListener = dbCollection
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

    /**
     * A method to detach the listener created from userListener.
     */
    public fun detachListener(uid: String){
        userListener.remove()
    }
}