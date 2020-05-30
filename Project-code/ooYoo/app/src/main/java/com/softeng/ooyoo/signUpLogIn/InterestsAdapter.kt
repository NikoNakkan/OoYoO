package com.softeng.ooyoo.signUpLogIn

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.INTERESTS_ARRAY_LIST

/**
 * This adapter is used to better manage the list of interests and user's selection.
 */
class InterestsAdapter(private val context: Context?, private val interestsMap: ArrayList<String>) :
    RecyclerView.Adapter<InterestsAdapter.InterestsViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): InterestsViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context).inflate(R.layout.interests_signup_item, viewGroup, false)
        return InterestsViewHolder(view)
    }

    override fun onBindViewHolder(holder: InterestsViewHolder, position: Int) {
        holder.textView.text = interestsMap[position]
    }

    override fun getItemCount(): Int = interestsMap.size


    inner class InterestsViewHolder(view: View) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        var textView: TextView = view.findViewById(R.id.signupInterestsTextView)

        override fun onClick(view: View) {
            val clickedPosition: Int = adapterPosition
            val interest = INTERESTS_ARRAY_LIST[clickedPosition]

            if (context == null || context !is SignupInterestsActivity){
                return
            }

            if (!context.getHasInterest(interest)) {
                textView.background = ContextCompat.getDrawable(context, R.drawable.orange_curvy_button)
                context.setHasInterest(interest, true)
            }
            else{
                textView.background = ContextCompat.getDrawable(context, R.drawable.gray_light_button_curvy_boy)
                context.setHasInterest(interest, false)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }
}