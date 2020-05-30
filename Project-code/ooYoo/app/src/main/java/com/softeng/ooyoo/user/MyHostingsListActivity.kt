package com.softeng.ooyoo.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.softeng.ooyoo.R
import com.softeng.ooyoo.host.Hosting
import kotlinx.android.synthetic.main.activity_my_hostings_list.*

const val HOSTS_LIST_EXTRA_NAME = "hosts list extra name"

/**
 * This activity represents the GUI from which the user can see a list of his hosting.
 */
class MyHostingsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_hostings_list)



        val hostings = intent.getParcelableArrayListExtra<Hosting>(HOSTS_LIST_EXTRA_NAME)
            ?: return

        myHostsListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyHostingsListActivity)
            setHasFixedSize(true)
            adapter = MyHostingsListAdapter(this@MyHostingsListActivity, hostings)
        }

    }
}
