package com.softeng.ooyoo.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.TripPlansDB
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.checkIfDateIsFuture
import com.softeng.ooyoo.helpers.dateDistance
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
import com.softeng.ooyoo.trip.TripPlan
import kotlinx.android.synthetic.main.activity_add_home.*


class AddHomeInfo : AppCompatActivity() {
    private val endTravelDate = mutableMapOf<String, Int>()
    private val dates = Dates()
    private val place = Place()
    val uid = FirebaseAuth.getInstance().uid
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_home)

        finishHostForm.setOnClickListener {
            val uid = FirebaseAuth.getInstance().uid


                val trip = TripPlan(uid = uid, place = place, dates = dates)

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are you sure you want to register this trip?")
                    .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->

                        val tripPlansDB = TripPlansDB()

                        tripPlansDB.tripRegistration(this, trip) { id ->

                            finish()
                        }

                    }
                    .setNegativeButton("No") { _: DialogInterface, _: Int ->
                        finish()
                    }
                    .setNeutralButton("Alter") { _: DialogInterface, _: Int ->

                    }
                val dialog = builder.create()
                dialog.show()


        }



        finishHostForm.setOnClickListener {
            if (uid == null) {
                toast("There was an error while authenticating you.")
            } else if (place.name == "" || dates.startDate.isEmpty() || dates.endDate.isEmpty()) {
                toast("Please add the place and the dates of your visit.")
            } else if (dateDistance(dates.startDate, dates.endDate) < 0) {
                toast("The starting date needs to be before the ending date.")
            } else if (!checkIfDateIsFuture(dates.startDate)) {
                toast("The date you selected has already passed. Please select a future date.")
            } else {
                val intent = Intent(this, AddHomeInfo::class.java)
                startActivity(intent)
            }
        }

    }
}