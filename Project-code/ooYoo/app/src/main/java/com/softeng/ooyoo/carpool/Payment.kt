package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize

class Payment(private val tripAmount: Int=0,
              private val typeOfPayment: String="")