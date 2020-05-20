package com.softeng.ooyoo.mainScreens

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.softeng.ooyoo.R
import com.softeng.ooyoo.user.BecomeTravellerActivity
import kotlinx.android.synthetic.main.fragment_search_for_travelers.*


class SearchForTravelersFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_search_for_travelers, container, false)

        if(context == null){
            return view
        }

       //val tempIntentTextView = view.findViewById<TextView>(R.id.temp_intent_text_view)


     /*   tempIntentTextView.setOnClickListener {
            val intent = Intent(context, BecomeTravellerActivity::class.java)
            startActivity(intent)
        }
*/
        return view

}
}
