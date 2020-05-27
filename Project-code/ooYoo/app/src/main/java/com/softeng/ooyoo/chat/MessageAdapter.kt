package com.softeng.ooyoo.chat

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.user.User

const val MESSAGE_RIGHT = 0
const val MESSAGE_LEFT = 1

class MessageAdapter(val context: Context, private val messages: ArrayList<Message>, private val uid: String, private val receiver: User) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return if (viewType == MESSAGE_RIGHT) {
            val view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false)
            MessageViewHolder(view, viewType)
        } else {
            val view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false)
            MessageViewHolder(view, viewType)
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.messageTextView.text = messages[position].text

        if(holder.viewType == MESSAGE_LEFT && holder.userImage != null){
            Glide.with(context).load(R.drawable.logo).centerCrop().circleCrop().into(holder.userImage)
        }

    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {

        return if (messages[position].senderId == uid) {
            MESSAGE_RIGHT
        }
        else {
            MESSAGE_LEFT
        }
    }



    class MessageViewHolder(itemView: View, val viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val messageTextView: TextView = itemView.findViewById(R.id.chat_item_textView_bubble)
        val userImage: ImageView? = itemView.findViewById(R.id.message_fragment_recycler_item_user_image)

    }

}