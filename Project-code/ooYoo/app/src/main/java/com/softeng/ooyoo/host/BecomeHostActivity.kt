package com.softeng.ooyoo.host

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
import kotlinx.android.synthetic.main.activity_become_host.*

/**
 * This activity represents the GUI from which the user adds details (place, dates) about a hosting.
 */
class BecomeHostActivity : AppCompatActivity() {
    private val dates = Dates()
    private val place = Place()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_become_host)

        val uid = FirebaseAuth.getInstance().uid

        becomeHostWhere.setOnClickListener {
            addLocation(supportFragmentManager) { country ->
                becomeHostWhereTextView.text = country
                place.name = country
            }
        }

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
}
