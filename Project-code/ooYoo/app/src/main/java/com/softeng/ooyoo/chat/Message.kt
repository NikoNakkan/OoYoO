package com.softeng.ooyoo.chat

class Message(private val senderId: String, private val receiverId: String, private val message: String, private val timestamp: Long, private val read: Boolean, private val messageId: String)