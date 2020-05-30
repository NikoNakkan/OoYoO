package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A class containing the payment information for a carpooling.
 */
@Parcelize
class Payment(
    private val tripAmount: Int=0,
    private val typeOfPayment: String=""
): Parcelable