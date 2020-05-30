package com.softeng.ooyoo.travel

import com.softeng.ooyoo.user.User

/**
 * This class is combining the User and the TravelEvent classes,
 * representing a travel event done by this user.
 * This is done to work easier with those classes.
 */
class UserAndTravelEvent(val user: User, val travelEvent: com.softeng.ooyoo.travel.TravelEvent)