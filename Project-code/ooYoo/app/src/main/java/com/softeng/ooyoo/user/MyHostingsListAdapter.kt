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
import com.softeng.ooyoo.host.Hosting

/**
 * An adapter used to better manage the list of user's hostings.
 */
class MyHostingsListAdapter(private val context: Context, private var hostings: ArrayList<Hosting>): RecyclerView.Adapter<MyHostingsListAdapter.HostListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_hosts_item, parent, false)

        hostings = sortHostings(hostings)

        return HostListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HostListViewHolder, position: Int) {
        holder.whereHostListTV.append(hostings[position].place.name)
        holder.whenFromHostListTV.append(dateMapToString(hostings[position].dates.startDate))
        holder.whenToHostListTV.append(dateMapToString(hostings[position].dates.endDate))

        if(System.currentTimeMillis() > hostings[position].endDateInMillis) {
            holder.myHostListLayout.background = ContextCompat.getDrawable(context, R.drawable.even_grayer_curvy)
        }

        holder.myHostListLayout.setOnClickListener {
            listItemClick()
        }
    }

    override fun getItemCount() = hostings.size

    /**
     * A method to sort the hostings.
     * First we set the future hostings in chronological order
     * and then the past hostings in chronological order.
     */
    private fun sortHostings(hostings: ArrayList<Hosting>): ArrayList<Hosting>{
        val list1 = arrayListOf<Hosting>()
        val list2 = arrayListOf<Hosting>()

        for (hosting in hostings){
            if(System.currentTimeMillis() > hosting.endDateInMillis){
                list2.add(hosting)
            }
            else{
                list1.add(hosting)
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

    /**
     * This method takes the user to an activity from which he can edit his Hostings.
     */
    private fun listItemClick(){
        context.toast("This feature is not implemented yet.")
    }

    /**
     * A view holder to increase adapter's performance.
     */
    class HostListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val myHostListLayout: RelativeLayout = itemView.findViewById(R.id.my_host_list_layout)
        val whereHostListTV: TextView = itemView.findViewById(R.id.where_my_host_list_item_text_view)
        val whenFromHostListTV: TextView = itemView.findViewById(R.id.host_list_item_from_textview)
        val whenToHostListTV: TextView = itemView.findViewById(R.id.host_list_item_to_textview)
    }


}