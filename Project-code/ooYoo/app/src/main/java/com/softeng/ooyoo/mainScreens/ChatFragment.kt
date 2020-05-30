package com.softeng.ooyoo.mainScreens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.softeng.ooyoo.R
import com.softeng.ooyoo.chat.Chat
import com.softeng.ooyoo.chat.ChatListAdapter
import com.softeng.ooyoo.databases.ChatDB
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.longToast
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.user.User


/**
 * This fragment represents the GUI from which a user can open a chat.
 */
class ChatFragment : Fragment(), PassUser {

    private var user = User()
    private var chats = arrayListOf<Chat>()
    private lateinit var recyclerView: RecyclerView
    private val userMap = mutableMapOf<String, User>()
    private val chatDB = ChatDB()
    private val userDB = UserDB()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        recyclerView = view.findViewById(R.id.recycler_of_friends_message_fragment)

        val searchEditText = view.findViewById<EditText>(R.id.search_users_edit_text)

        val adapter = ChatListAdapter(requireContext(), user, chats, userMap)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter




        searchEditText.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                context?.toast("This feature is not implemented yet")
            }
        }

        listenForMessages()


        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        chatDB.detachListener()
    }

    /**
     * Set the current user.
     */
    override fun setUser(user: User){
        this.user = user
    }

    /**
     * This method creates a listener that listens for new messages.
     */
    private fun listenForMessages(){

        chatDB.messageListener(user.uid){ newChat ->

            if(!chats.containsChat(newChat)){
                chats.add(newChat)

                val otherUid = newChat.getTheOtherUid(user.uid)
                    ?: return@messageListener

                userDB.retrieveSearchedUser(
                    otherUid,
                    onSuccess = { users ->
                        userMap[otherUid] = users[0]

                        chats.sortByDescending {
                            it.lastMessageSent
                        }

                        if(context != null) {
                            recyclerView.adapter = ChatListAdapter(requireContext(), user, chats, userMap)
                        }
                    },
                    onFailure = {
                        context?.longToast("An error occurred while retrieving data for your chat. Please try again.")
                    }
                )
            }
            else{
                Log.d(ChatFragment::class.java.simpleName, newChat.getChatId())
                for(i in 0 until chats.size){
                    if (chats[i].getChatId() == newChat.getChatId()){
                        chats[i] = newChat
                    }

                    chats.sortByDescending {
                        it.lastMessageSent
                    }

                    if(context != null) {
                        recyclerView.adapter = ChatListAdapter(requireContext(), user, chats, userMap)
                    }
                }
            }
        }
    }

    /**
     * This method checks if an array list of chats contains a specific chat.
     */
    private fun ArrayList<Chat>.containsChat(chat: Chat): Boolean{
        for (item in this){
            if (item.getChatId() == chat.getChatId()){
                return true
            }
        }
        return false
    }
}