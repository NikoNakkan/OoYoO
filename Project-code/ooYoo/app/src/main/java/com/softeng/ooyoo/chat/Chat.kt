package com.softeng.ooyoo.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Chat(
    private val uids: ArrayList<String> = arrayListOf(),
    private val messages: ArrayList<Message> = arrayListOf(),
    private val chatId: String=""
): Parcelable