package com.softeng.ooyoo.chat

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

/**
 * A class representing a chat between 2 users
 */
@Parcelize
class Chat(
    val uids: ArrayList<String> = arrayListOf(),
    val messages: ArrayList<Message> = arrayListOf(),
    val lastMessageSent: Timestamp = Timestamp.now(),
    private var id: String = ""
): Parcelable{

    /**
     * A setter for the chatId.
     * The chatId needs to be declared as private to avoid storing it as an extra field in firebase.
     */
    public fun setChatId(id: String){
        this.id = id
    }

    /**
     * A getter for the chatId.
     * The chatId needs to be declared as private to avoid storing it as an extra field in firebase.
     */
    public fun getChatId(): String{
        return id
    }

    /**
     * This gets a uid as an input and return the other uid that is in the uids field of the chat.
     * (The uids field contains the 2 uids of the users chatting).
     */
    public fun getTheOtherUid(uid: String): String?{
        for(i in 0..1){
            if (uids[i] != uid){
                return uids[i]
            }
        }

        return null
    }

}