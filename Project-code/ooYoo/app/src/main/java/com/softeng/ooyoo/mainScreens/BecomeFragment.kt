package com.softeng.ooyoo.mainScreens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.host.BecomeHostActivity
import com.softeng.ooyoo.trip.BECOME_USER_EXTRA_NAME
import com.softeng.ooyoo.trip.BecomeTravellerActivity
import com.softeng.ooyoo.user.User


class BecomeFragment : Fragment(), PassUser {

    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?    ): View? {
        val view = inflater.inflate(R.layout.fragment_become, container, false)

        val tripPlanButton = view.findViewById<CardView>(R.id.trip_plan_button)
        val carpoolingButton = view.findViewById<CardView>(R.id.carpooling_button)
        val hostButton = view.findViewById<CardView>(R.id.hostButton)

        tripPlanButton.setOnClickListener{
            val intent = Intent(context, BecomeTravellerActivity::class.java)
            intent.putExtra(BECOME_USER_EXTRA_NAME, user)
            startActivity(intent)
        }

        carpoolingButton.setOnClickListener {
            context?.toast("This feature is not implemented yet.")
        }

        hostButton.setOnClickListener{
            val intent = Intent(context, BecomeHostActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun setUser(user: User){
        this.user = user
    }

}
