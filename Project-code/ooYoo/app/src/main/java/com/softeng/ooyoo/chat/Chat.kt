package com.softeng.ooyoo.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Chat(
    val uids: ArrayList<String> = arrayListOf(),
    val messages: ArrayList<Message> = arrayListOf(),
    val lastMessageSent: Long = 0
): Parcelable