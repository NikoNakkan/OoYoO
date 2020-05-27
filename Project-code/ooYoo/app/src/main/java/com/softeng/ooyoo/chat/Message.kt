package com.softeng.ooyoo.chat

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Timestamp = Timestamp.now(),
    val read: Boolean = false
): Parcelable{

    constructor(map: HashMap<*, *>) : this(
        map["senderId"].toString(),
        map["receiverId"].toString(),
        map["text"].toString(),
        map["timestamp"] as Timestamp,
        map["read"] as Boolean)

}