package com.softeng.ooyoo.chat

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

/**
 * A class representing one message.
 */
@Parcelize
class Message(
    var senderId: String = "",
    var receiverId: String = "",
    var text: String = "",
    var timestamp: Timestamp = Timestamp.now(),
    val read: Boolean = false
): Parcelable{

    /**
     * An extra constructor to get the message from a map.
     * (This is used to get a message when we retrieve a map from firebase.
     */
    constructor(map: HashMap<*, *>) : this(
        map["senderId"].toString(),
        map["receiverId"].toString(),
        map["text"].toString(),
        map["timestamp"] as Timestamp,
        map["read"] as Boolean)

}