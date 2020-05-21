package com.softeng.ooyoo.databases

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.user.User

class UserDB: Database(USERS) {

    fun retrieveSearchedUsers(uids: ArrayList<String>, onSuccess: (ArrayList<User>) -> Unit){
        val db = FirebaseFirestore.getInstance()

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
                Log.e(UserDB::class.java.simpleName, "There was an error.", e)

            }
    }

}