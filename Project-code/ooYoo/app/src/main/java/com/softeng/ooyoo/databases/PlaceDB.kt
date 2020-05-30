package com.softeng.ooyoo.databases

import com.softeng.ooyoo.place.Place

/**
 * This class provides communication with the database for anything related with the places.
 */
class PlaceDB: Database(PLACES) {

    public fun retrievePlaces(placeId: String): ArrayList<Place>{
        TODO("Not implemented yet.")
    }

}