package com.softeng.ooyoo.databases

/**
 * A super class for all the database interfaces used.
 * All the other database class inherit from this.
 * The database class are not actual databases, they are a way to better communicate with
 * firebase cloud storage, the actual database of the app.
 */
abstract class Database(protected val collection: String)