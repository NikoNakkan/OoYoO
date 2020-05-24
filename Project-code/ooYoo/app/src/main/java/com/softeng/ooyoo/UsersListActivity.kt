package com.softeng.ooyoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeng.ooyoo.databases.*
import com.softeng.ooyoo.helpers.matching
import com.softeng.ooyoo.helpers.mergeLists
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.signUpLogIn.USER_EXTRA_NAME
import com.softeng.ooyoo.travel.UserAndTravelEvent
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_users_list.*

class UsersListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        val travelers = intent.getParcelableArrayListExtra<User>(TRAVELERS_EXTRA_NAME)
        val tripPlans = intent.getParcelableArrayListExtra<TripPlan>(TRIPS_EXTRA_NAME)
        val hostings = intent.getParcelableArrayListExtra<Hosting>(HOSTINGS_EXTRA_NAME)
        val hosts = intent.getParcelableArrayListExtra<User>(HOSTS_EXTRA_NAME)
        val user = intent.getParcelableExtra<User>(USER_EXTRA_NAME)

        if(travelers == null || tripPlans == null || hosts == null || hostings == null || user == null || user.username == ""){
            toast("Unfortunately an error occurred, please try again.")
            finish()
            return
        }

        val tripList = mergeLists<TripPlan>(travelers, tripPlans)
        val hostList = mergeLists<Hosting>(hosts, hostings)

        val list = arrayListOf<UserAndTravelEvent>()
        list.addAll(tripList)
        list.addAll(hostList)

        list.sortByDescending {
            matching(it.user.interests, user.interests)
        }


        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = UserAdapter(this, list)

    }
}
