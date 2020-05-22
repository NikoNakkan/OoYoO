package com.softeng.ooyoo.mainScreens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.UsersListActivity
import com.softeng.ooyoo.databases.TRAVELERS_EXTRA_NAME
import com.softeng.ooyoo.databases.TRIPS_EXTRA_NAME
import com.softeng.ooyoo.databases.TravelEventDB
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.signUpLogIn.USER_EXTRA_NAME
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.travel.Dates
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.User


class SearchForTravelersFragment : Fragment() {

    private val endTravelDate = mutableMapOf<String, Int>()
    private val dates = Dates()
    private val place = Place()
    private var user = User()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_search_for_travelers, container, false)

//        val tempIntentTextView = view.findViewById<TextView>(R.id.temp_intent_text_view)
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
            pickCountry(activity!!.supportFragmentManager){ country ->
                searchWhereTextView.text = country
                place.name = country
            }
        }

        searchWhenFrom.setOnClickListener {
            pickDate(context!!) { date ->
                searchWhenFromTextView.text = dateMapToString(date)
                dates.startDate = date
            }
        }

        searchWhenTo.setOnClickListener {
            pickDate(context!!) { date ->
                searchWhenToTextView.text = dateMapToString(date)
                dates.endDate = date
            }
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
                val uid = FirebaseAuth.getInstance().uid
                val tripPlan = TripPlan(uid, place, dates)
                val travelEventDB = TravelEventDB()
                travelEventDB.findRelevantTripPlans(context!!, tripPlan){ tripPlans: ArrayList<TripPlan>, travelers: ArrayList<User> ->
                    val intent = Intent(context, UsersListActivity::class.java)
                    intent.putParcelableArrayListExtra(TRAVELERS_EXTRA_NAME, travelers)
                    intent.putParcelableArrayListExtra(TRIPS_EXTRA_NAME, tripPlans)
                    intent.putExtra(USER_EXTRA_NAME, user)
                    startActivity(intent)
                }
                //get all relevant trips (based on place and date --> use whee queries)
                //get all users from uid of trips
                    // (for this to happen we need to either get all users and then test their uid
                    // or use a query with "in" if it exists on Firebase)
                //use similarity function on users
                //open a list activity which contains a recycler view with the users
            }

        }

        return view
    }

    fun setUser(user: User){
        this.user = user
    }

}
