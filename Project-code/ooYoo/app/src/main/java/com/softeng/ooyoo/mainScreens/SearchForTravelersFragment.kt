package com.softeng.ooyoo.mainScreens

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
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
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.User
import java.util.concurrent.Semaphore

/**
 * This fragment represents the GUI from which a user can search for other users.
 */
class SearchForTravelersFragment : Fragment(), PassUser {

    private val dates = Dates()
    private val place = Place()
    private var user = User()
    private val s = Semaphore(2, true)
    private var queriesFailed = 0
    private var noUsers = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_search_for_travelers, container, false)

        val searchWhere = view.findViewById<RelativeLayout>(R.id.searchTravelersWhere)
        val searchWhereTextView = view.findViewById<TextView>(R.id.searchTravelersWhereTextView)
        val searchWhenFrom = view.findViewById<RelativeLayout>(R.id.searchTravelerWhenFrom)
        val searchWhenFromTextView = view.findViewById<TextView>(R.id.searchTravelersWhenFromTV)
        val searchWhenTo = view.findViewById<RelativeLayout>(R.id.searchTravelerWhenTo)
        val searchWhenToTextView = view.findViewById<TextView>(R.id.searchTravelersWhenToTextView)
        val searchTravelerButton = view.findViewById<RelativeLayout>(R.id.searchTravelerButton)
        val searchCarpoolerButton = view.findViewById<RelativeLayout>(R.id.search_car_pooler_button)


        if(context == null || activity == null){
            return view
        }

        searchWhere.setOnClickListener {
            addLocation(searchWhereTextView)
        }

        searchWhenFrom.setOnClickListener {
            addDate(true, searchWhenFromTextView)
        }

        searchWhenTo.setOnClickListener {
            addDate(false, searchWhenToTextView)
        }

        searchTravelerButton.setOnClickListener {
            if(place.name == "" || dates.startDate.isEmpty() || dates.endDate.isEmpty()){
                context?.toast("Please add the place and the dates of your visit.")
            }
            else if(dateDistance(dates.startDate, dates.endDate) < 0){
                context?.toast("The starting date needs to be before the ending date.")
            }
            else if (!checkIfDateIsFuture(dates.startDate)){
                context?.toast("The date you selected has already passed. Please select a future date.")
            }
            else {
                searchForUsers()
            }

        }

        searchCarpoolerButton.setOnClickListener {
            context?.toast("This feature is not implemented yet.")
        }

        return view
    }

    /**
     * This method is used to select a location.
     */
    private fun addLocation(countryTextView: TextView){
        addLocation(requireActivity().supportFragmentManager){ country ->
            countryTextView.text = country
            place.name = country
        }
    }

    /**
     * This method is used to select a date.
     */
    private fun addDate(startEnd: Boolean, dateTextView: TextView){
        addDate(requireContext()) { date ->
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
     * This method is used to search for users to travel with or to host the current user.
     */
    private fun searchForUsers(){
        val intent = Intent(context, UsersListActivity::class.java)

        s.drainPermits()

        AsyncTask.execute(kotlinx.coroutines.Runnable {
            (activity as MainActivity).disableBottomNavigationView()
            findTravelersAndHosts(intent)

            Log.d(SearchForTravelersFragment::class.java.simpleName, queriesFailed.toString())

            s.acquire(2)
            if (queriesFailed < 2) {
                startActivity(intent)
                queriesFailed = 0
            }
            else{
                queriesFailed = 0
                if (noUsers){
                    errorMessage("Unfortunately there are no users for destination.")
                }
                else {
                    errorMessage("An error occurred while retrieving your data. Please check your Internet connection and try again.")
                }
            }
            (activity as MainActivity).enableBottomNavigationView()
        })
    }

    /**
     * Set the current user.
     */
    override fun setUser(user: User){
        this.user = user
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
        queriesFailed++
        this.noUsers = noUsers
        s.release()
    }

    /**
     * This method shows an error toast in the main thread.
     */
    private fun errorMessage(message: String){
        val mainHandler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            requireActivity().longToast(message)
        }
        mainHandler.post(runnable)
    }
}
