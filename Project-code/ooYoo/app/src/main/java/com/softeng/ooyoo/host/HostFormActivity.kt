package com.softeng.ooyoo.host

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
import kotlinx.android.synthetic.main.activity_host_form.*

/**
 * This activity represents the GUI from which the user adds details (place, dates) about a hosting.
 */
class HostFormActivity : AppCompatActivity() {
    private val dates = Dates()
    private val place = Place()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_form)

        val uid = FirebaseAuth.getInstance().uid

        becomeHostWhere.setOnClickListener {
            addLocation(becomeHostWhereTextView)
        }

        becomeHostWhenFrom.setOnClickListener {
            addDate(true, becomeHostWhenFromTextView)
        }

        becomeHostWhenTo.setOnClickListener {
            addDate(false, becomeHostWhenToTextView)
        }

        addHomeInfo.setOnClickListener{
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
                val intent = Intent(this, AddHomeInfoActivity::class.java)
                intent.putExtra(HOST_PLACE_EXTRA_NAME, place)
                intent.putExtra(HOST_DATES_EXTRA_NAME, dates)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
    }

    /**
     * This method is used to select a location.
     */
    private fun addLocation(countryTextView: TextView){
        addLocation(this.supportFragmentManager){ country ->
            countryTextView.text = country
            place.name = country
        }
    }

    /**
     * This method is used to select a date.
     */
    private fun addDate(startEnd: Boolean, dateTextView: TextView){
        addDate(this) { date ->
            dateTextView.text = dateMapToString(date)
            if(startEnd) {
                dates.startDate = date
            }
            else{
                dates.endDate = date
            }
        }
    }
}
