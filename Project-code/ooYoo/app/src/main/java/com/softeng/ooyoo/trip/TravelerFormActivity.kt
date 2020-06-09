package com.softeng.ooyoo.trip

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.user.UsersListActivity
import com.softeng.ooyoo.databases.*
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.signUpLogIn.USER_EXTRA_NAME
import com.softeng.ooyoo.travel.Dates
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_traveler_form.*
import java.util.concurrent.Semaphore

const val BECOME_USER_EXTRA_NAME = "become user extra name"

/**
 * This activity represents the GUI from which the user adds details (place, dates) about a trip plan.
 */
class BecomeTravellerActivity : AppCompatActivity() {

    private val dates = Dates()
    private val place = Place()
    private val s = Semaphore(2, true)
    private var queriesFailed = false
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traveler_form)

        val uid = FirebaseAuth.getInstance().uid
        user = intent.getParcelableExtra(BECOME_USER_EXTRA_NAME) ?: User(uid = "_")

        becomeTravellerWhere.setOnClickListener {
            addLocation(becomeTravellerWhereTextView)
        }

        becomeTravellerWhenFrom.setOnClickListener {
            addDate(true, becomeTravellerWhenFromTextView)
        }

        becomeTravellerWhenTo.setOnClickListener {
            addDate(false, becomeTravellerWhenToTextView)
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
                        travelerRegistration(uid, trip)
                    }
                    .setNegativeButton("No") { _: DialogInterface, _: Int ->
                        declineRegistration()
                    }
                    .setNeutralButton("Alter"){ _: DialogInterface, _: Int ->

                    }
                val dialog = builder.create()
                dialog.show()

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

    /**
     * This is method is used to register a TripPlan
     */
    private fun travelerRegistration(uid: String, trip: TripPlan){
        val tripPlansDB = TripPlansDB()

        tripPlansDB.tripRegistration(this, trip) { id ->
            val userDB = UserDB()
            userDB.uploadTripOnDatabase(uid, id)

            if(user.uid != "_") {
                openUserList()
            }
            else{
                finish()
            }
        }
    }

    /**
     * This method is used to decline a registration.
     */
    private fun declineRegistration(){
        finish()
    }

    /**
     * This method opens a list of users with similar trips when the user finishes setting up his.
     */
    private fun openUserList(){
        val intent = Intent(this, UsersListActivity::class.java)

        s.drainPermits()

        AsyncTask.execute(kotlinx.coroutines.Runnable {
            findTravelersAndHosts(intent)

            s.acquire(2)
            if (!queriesFailed) {
                startActivity(intent)
            }
            else{
                queriesFailed = false
            }
            finish()
        })
    }

    /**
     * This method is used to find for other users who travel or host in similar dates
     * as the current user's trip.
     */
    private fun findTravelersAndHosts(intent: Intent){
        val uid = FirebaseAuth.getInstance().uid

        intent.putExtra(USER_EXTRA_NAME, user)

        val tripPlan = TripPlan(uid, place, dates)
        val tripPlanDB = TripPlansDB()

        tripPlanDB.findRelevantTripPlans(
            tripPlan,
            onSuccess = { tripPlans: ArrayList<com.softeng.ooyoo.travel.TravelEvent>, travelers: ArrayList<User> ->
                intent.putParcelableArrayListExtra(TRIPS_EXTRA_NAME, tripPlans)
                intent.putParcelableArrayListExtra(TRAVELERS_EXTRA_NAME, travelers)
                s.release()
            },
            onFailure = ::onFailure
        )

        val hosting = Hosting(uid, place, dates)
        val hostingDB = HostingDB()

        hostingDB.findRelevantHostings(
            hosting,
            onSuccess = { hostings: ArrayList<com.softeng.ooyoo.travel.TravelEvent>, hosts: ArrayList<User> ->
                intent.putParcelableArrayListExtra(HOSTINGS_EXTRA_NAME, hostings)
                intent.putParcelableArrayListExtra(HOSTS_EXTRA_NAME, hosts)
                s.release()
            },
            onFailure = ::onFailure
        )

    }

    /**
     * This method show an error message if searching for other users fail.
     */
    private fun onFailure(noUsers: Boolean){
        queriesFailed = true

        if (noUsers){
            longToast("Unfortunately there are no users for destination ")
        }
        else {
            longToast("An error occurred while retrieving your data. Please check your Internet connection and try again.")
        }

        s.release()
    }

}
