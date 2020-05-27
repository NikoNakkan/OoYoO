package com.softeng.ooyoo.chat

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
class Chat(
    val uids: ArrayList<String> = arrayListOf(),
    val messages: ArrayList<Message> = arrayListOf(),
    val lastMessageSent: Timestamp = Timestamp.now(),
    private var id: String = ""
): Parcelable{

    public fun setChatId(id: String){
        this.id = id
    }

    public fun getChatId(): String{
        return id
    }

    public fun getTheOtherUid(uid: String): String?{
        for(i in 0..1){
            if (uids[i] != uid){
                return uids[i]
            }
        }

        return null
    }

}