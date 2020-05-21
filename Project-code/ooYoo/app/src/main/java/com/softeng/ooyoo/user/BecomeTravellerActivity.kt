package com.softeng.ooyoo.user

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.TravelEventDB
import com.softeng.ooyoo.longToast
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.travel.Dates
import com.softeng.ooyoo.trip.TripPlan
import com.ybs.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_become_traveller.*
import kotlinx.android.synthetic.main.activity_become_traveller.becomeTravellerWhen

class BecomeTravellerActivity : AppCompatActivity() {

    private var country: String = ""
    private val startTravelDate = mutableMapOf<String, Int>()
    private val endTravelDate = mutableMapOf<String, Int>()
    private val dates = Dates()
    private val place = Place()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_become_traveller)

        val uid = FirebaseAuth.getInstance().uid

        becomeTravellerWhere.setOnClickListener {
            val countryPicker = CountryPicker.newInstance("Select Country")
            countryPicker.setListener{ name: String, _: String, _: String, _: Int ->
                becomeTravellerWhereTextView.text = name
                country = name
                countryPicker.dismiss()
                place.name = country
            }
            countryPicker.show(supportFragmentManager, "COUNTRY_PICKER")
        }

      /*  becomeTravellerWhen.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, year: Int, month: Int, day: Int ->
                    startTravelDate["Year"] = year
                    startTravelDate["Month"] = month
                    startTravelDate["Day"] = day
                    becomeTravellerWhenTextView.text = dateMapToString(startTravelDate)
                    dates.startDate = startTravelDate
                },
                2020,
                1,
                1
            )
            datePickerDialog.show()
        }

       */

        //TODO add another date picker, in it set the end date
        endTravelDate["Year"] = 2000
        endTravelDate["Month"] = 0
        endTravelDate["Day"] = 1
        dates.endDate = endTravelDate


        becomeTravellerButton.setOnClickListener {
            if (uid == null){
                toast("There was an error while authenticating you.")
            }
            else if(place.name == "" || dates.startDate.isEmpty() || dates.endDate.isEmpty()){
                toast("Please add the place and the dates of your visit.")
            }
            else {
                val trip = TripPlan(uid=uid, place = place, dates = dates)

                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are you sure you want to register this trip?")
                    .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->
                        val travelEventDB = TravelEventDB()
                        travelEventDB.tripRegistration(this, trip, ::finish)
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

    private fun dateMapToString(m: MutableMap<String, Int>) = m["Day"].toString() + "/" + m["Month"].toString() + "/" + m["Year"].toString()
}
