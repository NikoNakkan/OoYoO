package com.softeng.ooyoo.mainScreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.softeng.ooyoo.R
import com.softeng.ooyoo.chat.ChatListAdapter
import com.softeng.ooyoo.databases.ChatDB
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.getUidsFromChats
import com.softeng.ooyoo.longToast
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.user.User


class ChatFragment : Fragment() {

    private var user = User()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_of_friends_message_fragment)
        val searchEditText = view.findViewById<EditText>(R.id.search_users_edit_text)

        val chatDB = ChatDB()

        val userMap = mutableMapOf<String, User>()


        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        chatDB.retrieveChatData(user.chats){ chats ->

            chats.sortByDescending {
                it.lastMessageSent
            }

            val uids = arrayListOf<String>()
            uids.addAll(getUidsFromChats(user.uid, chats))

            val userDB = UserDB()
            userDB.retrieveSearchedUsers(
                uids,
                onSuccess = { users ->
                    for(user in users){
                        userMap[user.uid] = user
                    }

                    if(context != null) {
                        val adapter = ChatListAdapter(context!!, user.uid, chats, userMap)
                        recyclerView.adapter = adapter
                    }

                },
                onFailure = {
                    context?.longToast("An error occurred while retrieving data for your chat. Please try again.")
                }
            )
        }


        searchEditText.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                context?.toast("This feature is not implemented yet")
            }
        }

        return view
    }

    fun setUser(user: User){
        this.user = user
    }

}
