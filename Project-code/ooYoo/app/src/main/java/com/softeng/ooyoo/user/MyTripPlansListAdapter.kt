package com.softeng.ooyoo.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.dateMapToString
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.trip.TripPlan

class MyTripPlansListAdapter(val context: Context, val tripPlans: ArrayList<TripPlan>): RecyclerView.Adapter<MyTripPlansListAdapter.TripListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_trips_item, parent, false)

        return TripListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripListViewHolder, position: Int) {
        holder.whereTripListTV.append(tripPlans[position].place.name)
        holder.whenFromTripListTV.append(dateMapToString(tripPlans[position].dates.startDate))
        holder.whenToTripListTV.append(dateMapToString(tripPlans[position].dates.endDate))

        holder.myTripListLayout.setOnClickListener {
            context.toast("This feature is not implemented yet.")
        }
    }

    override fun getItemCount() = tripPlans.size


    class TripListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val myTripListLayout: RelativeLayout = itemView.findViewById(R.id.my_trip_list_layout)
        val whereTripListTV: TextView = itemView.findViewById(R.id.where_my_trips_list_item_text_view)
        val whenFromTripListTV: TextView = itemView.findViewById(R.id.trips_list_item_from_textview)
        val whenToTripListTV: TextView = itemView.findViewById(R.id.trips_list_item_to_textview)
    }
}