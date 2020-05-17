package com.softeng.ooyoo.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Message(
    private val senderId: String = "",
    private val receiverId: String = "",
    private val message: String = "",
    private val timestamp: Long = 0,
    private val read: Boolean = false,
    private val messageId: String = ""
): Parcelable