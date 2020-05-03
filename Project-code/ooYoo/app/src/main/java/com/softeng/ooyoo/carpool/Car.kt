package com.softeng.ooyoo.carpool

import java.sql.Timestamp

class Car(private val type: String, private val carUse: String, private val carState: Boolean, private val carServiceDate: Timestamp, private val safety: String, private val carFeatures: CarFeatures, private val carSpecs: CarSpecs)