package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Trip(
    val tripDetails: TripDetails = TripDetails(),
    val payment: Payment = Payment()
):Parcelable