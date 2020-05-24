package com.softeng.ooyoo.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.softeng.ooyoo.R
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.user.User

class ChatListAdapter(
    private val context: Context,
    private val uid: String,
    private val chats: ArrayList<Chat>,
    private val userMap: MutableMap<String, User>)
    : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item, parent, false)

        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val receiverId =
            if(uid == chats[position].uids[0]){
                chats[position].uids[1]
            }
            else{
                chats[position].uids[0]
            }

        Glide.with(context).load(R.drawable.logo).centerCrop().circleCrop().into(holder.imageView)

        holder.userNameTextView.text = userMap[receiverId]?.fullName?.split(" ")?.get(0) ?: "User"

        if(chats[position].messages.size > 0) {
            holder.lastMessageTextView.text = chats[position].messages.last().text
        }
    }

    override fun getItemCount(): Int = chats.size

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val imageView: ImageView = itemView.findViewById(R.id.chatProfileImageView)
        val userNameTextView: TextView = itemView.findViewById(R.id.chatUserNameTextView)
        val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessageTextView)

        override fun onClick(v: View?) {
            v?.context?.toast("User in chat list is clicked.")
        }
    }


}