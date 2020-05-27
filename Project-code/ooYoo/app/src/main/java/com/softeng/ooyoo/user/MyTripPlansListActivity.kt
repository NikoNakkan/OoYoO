package com.softeng.ooyoo.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeng.ooyoo.R
import com.softeng.ooyoo.trip.TripPlan
import kotlinx.android.synthetic.main.activity_my_trip_plans_list.*

const val TRIPS_LIST_EXTRA_NAME = "trips list extra name"

class MyTripPlansListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_trip_plans_list)

        val tripPlans = intent.getParcelableArrayListExtra<TripPlan>(TRIPS_LIST_EXTRA_NAME)
            ?: return

        myTripsListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyTripPlansListActivity)
            setHasFixedSize(true)
            adapter = MyTripPlansListAdapter(this@MyTripPlansListActivity, tripPlans)
        }

    }
}
