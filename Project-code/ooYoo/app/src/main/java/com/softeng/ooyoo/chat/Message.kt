package com.softeng.ooyoo.chat

class Message(private val senderId: String="",
              private val receiverId: String="",
              private val message: String="",
              private val timestamp: String="",
              private val read: Boolean=false,
              private val messageId: String="")