package com.softeng.ooyoo.chat

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
            val messageText = chats[position].messages.last().text

            holder.lastMessageTextView.text = if(messageText.length <= 15) {
                messageText
            }
            else{
                messageText.subSequence(0, 14).toString() + "..."
            }
        }

        holder.chatLayout.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(USER_CHAT_EXTRA_NAME, userMap[receiverId])
            intent.putExtra(CHAT_EXTRA_NAME, chats[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = chats.size

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val chatLayout: LinearLayout = itemView.findViewById(R.id.chatItemLayout)
        val imageView: ImageView = itemView.findViewById(R.id.chatItemProfileImageView)
        val userNameTextView: TextView = itemView.findViewById(R.id.chatUserNameTextView)
        val lastMessageTextView: TextView = itemView.findViewById(R.id.lastMessageTextView)
    }


}