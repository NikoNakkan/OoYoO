package com.softeng.ooyoo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softeng.ooyoo.helpers.dateMapToString
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.travel.UserAndTravelEvent
import com.softeng.ooyoo.trip.TripPlan


class UserAdapter(private val context: Context, private val list: ArrayList<UserAndTravelEvent>): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    
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
    }

    override fun getItemCount(): Int = minOf(list.size, 20)

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.userItemProfileImageView)
        val userNameTextView: TextView = itemView.findViewById(R.id.chatUserNameTextView)
        val userTypeTextView: TextView = itemView.findViewById(R.id.userType)
        val userFromTextView: TextView = itemView.findViewById(R.id.userFrom)
        val userFromValuesTextView: TextView = itemView.findViewById(R.id.userFromValues)
        val userToTextView: TextView = itemView.findViewById(R.id.userTo)
        val userToValuesTextView: TextView = itemView.findViewById(R.id.userToValues)

    }

}