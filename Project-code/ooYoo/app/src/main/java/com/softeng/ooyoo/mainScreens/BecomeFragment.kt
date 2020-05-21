package com.softeng.ooyoo.mainScreens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.softeng.ooyoo.R
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.user.BecomeTravellerActivity


class BecomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?    ): View? {
        val view = inflater.inflate(R.layout.fragment_become, container, false)

        val tripPlanButton = view.findViewById<Button>(R.id.trip_plan_button)
        val carpoolingButton = view.findViewById<Button>(R.id.carpooling_button)
        val hostButton = view.findViewById<Button>(R.id.hosting_button)

        tripPlanButton.setOnClickListener{
            val intent = Intent(context, BecomeTravellerActivity::class.java)
            startActivity(intent)
        }

        carpoolingButton.setOnClickListener {
            context?.toast("This feature is not implemented yet.")
        }

        return view
    }

}