package com.softeng.ooyoo.mainScreens

import com.softeng.ooyoo.user.User

/**
 * This is an interface to ensure that all the main fragments implement a method to get the current user's data.
 */
interface PassUser {

    fun setUser(user: User)

}