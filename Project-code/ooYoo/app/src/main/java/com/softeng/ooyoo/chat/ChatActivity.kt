package com.softeng.ooyoo.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.softeng.ooyoo.R
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_chat.*

const val USER_CHAT_EXTRA_NAME = "user chat extra name"
const val CHAT_EXTRA_NAME = "chat extra name"

class ChatActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val user = intent.getParcelableExtra(USER_CHAT_EXTRA_NAME) ?: User(uid="_")
        val chat = intent.getParcelableExtra(CHAT_EXTRA_NAME) ?: Chat()
        val imageUrl = ""

        if(user.uid == "_" || chat.lastMessageSent == 0L){
            toast("There was an error while opening this chat.")
            finish()
        }

        Glide.with(this).load(imageUrl).placeholder(R.drawable.logo).circleCrop().into(chatActivityProfileImageView)
        chatActivityNameTextView.text = user.fullName.split(" ")[0]
        chatBackButton.setOnClickListener {
            onBackPressed()
        }

    }
}
