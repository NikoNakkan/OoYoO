package com.softeng.ooyoo.databases

import android.os.Message
import android.util.Log
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.chat.Chat

class ChatDB: Database(CHATS) {
    val dbCollection = FirebaseFirestore.getInstance().collection(this.collection)

    public fun sendTravelMessage(senderId: String, receiverId: String){

    }

    public fun sendHostMessage(senderId: String, receiverId: String){

    }

    public fun sendCarpoolingMessage(senderId: String, receiverId: String){

    }


    public fun sendMessage(chatId: String, senderId: String, receiverId: String, message: Message){
        val userDB = UserDB()

//        userDB.
    }



    public fun retrieveChatData(chatIds: ArrayList<String>, onSuccess: (ArrayList<Chat>) -> Unit){
        dbCollection
            .whereIn(FieldPath.documentId(), chatIds)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val chats = arrayListOf<Chat>()

                for (document in querySnapshot){
                    chats.add(document.toObject(Chat::class.java))
                }

                onSuccess(chats)
            }
    }

    public fun retrieveContact(uid0: String, uid1: String): ArrayList<Message>{

        return arrayListOf()
    }
}