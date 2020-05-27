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
import com.softeng.ooyoo.host.Hosting

class MyHostingsListAdapter(val context: Context, val hostings: ArrayList<Hosting>): RecyclerView.Adapter<MyHostingsListAdapter.HostListViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_hosts_item, parent, false)

        return HostListViewHolder(view)
    }

    override fun onBindViewHolder(holder: HostListViewHolder, position: Int) {
        holder.whereHostListTV.append(hostings[position].place.name)
        holder.whenFromHostListTV.append(dateMapToString(hostings[position].dates.startDate))
        holder.whenToHostListTV.append(dateMapToString(hostings[position].dates.endDate))

        holder.myHostListLayout.setOnClickListener {
            context.toast("This feature is not implemented yet.")
        }
    }

    override fun getItemCount() = hostings.size


    class HostListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val myHostListLayout: RelativeLayout = itemView.findViewById(R.id.my_host_list_layout)
        val whereHostListTV: TextView = itemView.findViewById(R.id.where_my_host_list_item_text_view)
        val whenFromHostListTV: TextView = itemView.findViewById(R.id.host_list_item_from_textview)
        val whenToHostListTV: TextView = itemView.findViewById(R.id.host_list_item_to_textview)
    }


}