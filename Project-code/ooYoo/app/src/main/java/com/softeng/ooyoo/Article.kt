package com.softeng.ooyoo

class Article (
    private val id: String,
    private val title: String,
    private val body: String,
    private val authorId: String,
    private val likes: Int,
    private val comments: ArrayList<String>)