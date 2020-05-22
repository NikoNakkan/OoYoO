package com.softeng.ooyoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeng.ooyoo.databases.TRAVELERS_EXTRA_NAME
import com.softeng.ooyoo.databases.TRIPS_EXTRA_NAME
import com.softeng.ooyoo.helpers.matching
import com.softeng.ooyoo.helpers.mergeLists
import com.softeng.ooyoo.signUpLogIn.USER_EXTRA_NAME
import com.softeng.ooyoo.travel.Dates
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_users_list.*

class UsersListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_list)

        val travelers = intent.getParcelableArrayListExtra<User>(TRAVELERS_EXTRA_NAME)
        val tripPlans = intent.getParcelableArrayListExtra<TripPlan>(TRIPS_EXTRA_NAME)
        val user = intent.getParcelableExtra<User>(USER_EXTRA_NAME)

        if(travelers == null || tripPlans == null || user == null || user.username == ""){
            toast("Unfortunately an error occurred, please try again.")
            finish()
            return
        }

        val list = mergeLists(travelers, tripPlans)
        list.sortBy { it ->
            matching(it.user.interests, user.interests)
        }


        usersRecyclerView.layoutManager = LinearLayoutManager(this)
        usersRecyclerView.adapter = UserAdapter(this, list)

    }
}
