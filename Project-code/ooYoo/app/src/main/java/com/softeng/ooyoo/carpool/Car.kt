package com.softeng.ooyoo.carpool

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Car(val specs: CarSpecs = CarSpecs(), val details: CarDetails = CarDetails()): Parcelable