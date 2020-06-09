package com.softeng.ooyoo.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.*
import com.softeng.ooyoo.helpers.matching
import com.softeng.ooyoo.helpers.mergeLists
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.signUpLogIn.USER_EXTRA_NAME
import com.softeng.ooyoo.travel.UserAndTravelEvent
import com.softeng.ooyoo.trip.TripPlan
import kotlinx.android.synthetic.main.activity_users_list.*
import kotlinx.android.synthetic.main.fragment_become.*

/**
 * This activity represents the GUI from which the user can see a list of other users..
 */
class UsersListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        val travelers = intent.getParcelableArrayListExtra<User>(TRAVELERS_EXTRA_NAME)
        val tripPlans = intent.getParcelableArrayListExtra<TripPlan>(TRIPS_EXTRA_NAME)
        val hostings = intent.getParcelableArrayListExtra<Hosting>(HOSTINGS_EXTRA_NAME)
        val hosts = intent.getParcelableArrayListExtra<User>(HOSTS_EXTRA_NAME)
        val user = intent.getParcelableExtra<User>(USER_EXTRA_NAME)

        if((travelers == null && hosts == null) || (tripPlans == null && hostings == null) || user == null || user.username == ""){
            toast("Unfortunately an error occurred, please try again.")
            finish()
            return
        }

        showUsers(travelers, tripPlans, hosts, hostings, user)
    }

    /**
     * This method is used to show the users in a list.
     */
    private fun showUsers(
        travelers: ArrayList<User>?,
        tripPlans: ArrayList<TripPlan>?,
        hosts: ArrayList<User>?,
        hostings: ArrayList<Hosting>?,
        user: User){

        val tripList = mergeLists(travelers, tripPlans)
        val hostList = mergeLists(hosts, hostings)

        val list = arrayListOf<UserAndTravelEvent>()
        list.addAll(tripList)
        list.addAll(hostList)

        list.sortByDescending {
            matching(it.user.interests, user.interests)
        }

        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.setHasFixedSize(true)
        usersRecyclerView.adapter = UserAdapter(this, list, user)
    }
}
