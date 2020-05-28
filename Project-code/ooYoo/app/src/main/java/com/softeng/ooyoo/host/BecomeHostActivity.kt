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
            addLocation(supportFragmentManager) { country ->
                becomeHostWhereTextView.text = country
                place.name = country
            }
        }

        // add place
        becomeHostWhenFrom.setOnClickListener {
            addDate(this) { date ->
                becomeHostWhenFromTextView.text = dateMapToString(date)
                dates.startDate = date
            }
        }

        becomeHostWhenTo.setOnClickListener {
            addDate(this){ date ->
                becomeHostWhenToTextView.text = dateMapToString(date)
                dates.endDate = date
            }
        }

        addHomeInfo.setOnClickListener{
            val intent = Intent(this, AddHomeInfoActivity::class.java)
            startActivity(intent)
        }


    }
}
