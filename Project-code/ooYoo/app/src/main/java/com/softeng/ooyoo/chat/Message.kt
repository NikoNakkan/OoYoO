package com.softeng.ooyoo.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Long = 0,
    val read: Boolean = false
): Parcelable