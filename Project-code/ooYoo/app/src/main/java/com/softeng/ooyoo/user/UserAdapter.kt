package com.softeng.ooyoo.user

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.dateMapToString
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.travel.UserAndTravelEvent
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.othersProfile.*

/**
 * An adapter used to better manage the list of users.
 */
class UserAdapter(private val context: Context, private val list: ArrayList<UserAndTravelEvent>, private val user: User) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        Glide.with(context).load(R.drawable.logo).centerCrop().circleCrop().into(holder.imageView)

        holder.userNameTextView.text = list[position].user.fullName.split(" ")[0]


        holder.userFromTextView.text = "From:"
        holder.userToTextView.text = "To:"

        if(list[position].travelEvent is TripPlan) {
            holder.userTypeTextView.text = "Traveling"
            holder.userFromValuesTextView.text =
                dateMapToString((list[position].travelEvent as TripPlan).dates.startDate)
            holder.userToValuesTextView.text =
                dateMapToString((list[position].travelEvent as TripPlan).dates.endDate)
        }

        if(list[position].travelEvent is Hosting){
            holder.userTypeTextView.text = "Hosting"
            holder.userFromValuesTextView.text =
                dateMapToString((list[position].travelEvent as Hosting).dates.startDate)
            holder.userToValuesTextView.text =
                dateMapToString((list[position].travelEvent as Hosting).dates.endDate)
        }

        holder.userListLayout.setOnClickListener {
            val intent = Intent(context, ProfileActivity::class.java)

            intent.putExtra(USER_PROFILE_EVENT_EXTRA_NAME, list[position].travelEvent)
            intent.putExtra(USER_PROFILE_CURRENT_EXTRA_NAME, user)
            intent.putExtra(USER_PROFILE_OTHER_EXTRA_NAME, list[position].user)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = minOf(list.size, 20)

    /**
     * A view holder to increase adapter's performance.
     */
    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val userListLayout: LinearLayout = itemView.findViewById(R.id.user_list_layout)
        val imageView: ImageView = itemView.findViewById(R.id.userItemProfileImageView)
        val userNameTextView: TextView = itemView.findViewById(R.id.chatUserNameTextView)
        val userTypeTextView: TextView = itemView.findViewById(R.id.userType)
        val userFromTextView: TextView = itemView.findViewById(R.id.userFrom)
        val userFromValuesTextView: TextView = itemView.findViewById(R.id.userFromValues)
        val userToTextView: TextView = itemView.findViewById(R.id.userTo)
        val userToValuesTextView: TextView = itemView.findViewById(R.id.userToValues)
    }

}