package com.softeng.ooyoo.user

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.TripPlansDB
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.travel.Dates
import com.softeng.ooyoo.trip.TripPlan
import kotlinx.android.synthetic.main.activity_become_traveller.*

class BecomeTravellerActivity : AppCompatActivity() {

    private val endTravelDate = mutableMapOf<String, Int>()
    private val dates = Dates()
    private val place = Place()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_become_traveller)

        val uid = FirebaseAuth.getInstance().uid

        becomeTravellerWhere.setOnClickListener {
            pickCountry(supportFragmentManager) { country ->
                becomeTravellerWhereTextView.text = country
                place.name = country
            }
        }

        becomeTravellerWhenFrom.setOnClickListener {
            pickDate(this) { date ->
                becomeTravellerWhenFromTextView.text = dateMapToString(date)
                dates.startDate = date
            }
        }

        becomeTravellerWhenTo.setOnClickListener {
            pickDate(this){ date ->
                becomeTravellerWhenToTextView.text = dateMapToString(date)
                dates.endDate = date
            }
        }



        becomeTravellerButton.setOnClickListener {
            if (uid == null){
                toast("There was an error while authenticating you.")
            }
            else if(place.name == "" || dates.startDate.isEmpty() || dates.endDate.isEmpty()){
                toast("Please add the place and the dates of your visit.")
            }
            else if (dateDistance(dates.startDate, dates.endDate) < 0){
                toast("The starting date needs to be before the ending date.")
            }
            else if (!checkIfDateIsFuture(dates.startDate)){
                toast("The date you selected has already passed. Please select a future date.")
            }
            else {
                val trip = TripPlan(uid=uid, place = place, dates = dates)

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are you sure you want to register this trip?")
                    .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->

                        val tripPlansDB = TripPlansDB()

                        tripPlansDB.tripRegistration(this, trip) { id ->
                            val userDB = UserDB()
                            userDB.uploadTripOnDatabase(uid, id)

                            finish()
                        }

                    }
                    .setNegativeButton("No") { _: DialogInterface, _: Int ->
                        finish()
                    }
                    .setNeutralButton("Alter"){ _: DialogInterface, _: Int ->

                    }
                val dialog = builder.create()
                dialog.show()

            }
        }

    }

}
