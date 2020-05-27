package com.softeng.ooyoo.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.dateMapToString
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.trip.TripPlan

class MyTripPlansListAdapter(private val context: Context, private var tripPlans: ArrayList<TripPlan>): RecyclerView.Adapter<MyTripPlansListAdapter.TripListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_trips_item, parent, false)


        tripPlans = sortTripPlanList(tripPlans)

        return TripListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripListViewHolder, position: Int) {
        holder.whereTripListTV.append(tripPlans[position].place.name)
        holder.whenFromTripListTV.append(dateMapToString(tripPlans[position].dates.startDate))
        holder.whenToTripListTV.append(dateMapToString(tripPlans[position].dates.endDate))

        if(System.currentTimeMillis() > tripPlans[position].endDateInMillis) {
            holder.myTripListLayout.background =
                ContextCompat.getDrawable(context, R.drawable.even_grayer_curvy)
        }

        holder.myTripListLayout.setOnClickListener {
            context.toast("This feature is not implemented yet.")
        }
    }

    override fun getItemCount() = tripPlans.size

    private fun sortTripPlanList(tripPlans: ArrayList<TripPlan>): ArrayList<TripPlan>{
        val list1 = arrayListOf<TripPlan>()
        val list2 = arrayListOf<TripPlan>()

        for (tripPlan in tripPlans){
            if(System.currentTimeMillis() > tripPlan.endDateInMillis){
                list2.add(tripPlan)
            }
            else{
                list1.add(tripPlan)
            }
        }


        list1.sortBy {
            it.startDateInMillis
        }

        list2.sortBy {
            it.startDateInMillis
        }

        list1.addAll(list2)

        return list1
    }

    class TripListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val myTripListLayout: RelativeLayout = itemView.findViewById(R.id.my_trip_list_layout)
        val whereTripListTV: TextView = itemView.findViewById(R.id.where_my_trips_list_item_text_view)
        val whenFromTripListTV: TextView = itemView.findViewById(R.id.trips_list_item_from_textview)
        val whenToTripListTV: TextView = itemView.findViewById(R.id.trips_list_item_to_textview)
    }
}