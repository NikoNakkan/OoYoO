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
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.travel.Dates
import com.softeng.ooyoo.trip.TripPlan
import kotlinx.android.synthetic.main.activity_become_host.*
import kotlinx.android.synthetic.main.activity_become_traveller.*

class BecomeHostActivity : AppCompatActivity() {
    //comment
    private val endTravelDate = mutableMapOf<String, Int>()
    private val dates = Dates()
    private val place = Place()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_become_host)

        val uid = FirebaseAuth.getInstance().uid

        // add date
        becomeHostWhere.setOnClickListener {
            pickCountry(supportFragmentManager) { country ->
                becomeHostWhereTextView.text = country
                place.name = country
            }
        }

        // add place
        becomeHostWhenFrom.setOnClickListener {
            pickDate(this) { date ->
                becomeTravellerWhenFromTextView.text = dateMapToString(date)
                dates.startDate = date
            }
        }

        becomeHostWhenTo.setOnClickListener {
            pickDate(this){ date ->
                becomeTravellerWhenToTextView.text = dateMapToString(date)
                dates.endDate = date
            }
        }


        addHomeInfo.setOnClickListener {
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


