package com.softeng.ooyoo.user.othersProfile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.chat.*
import com.softeng.ooyoo.databases.ChatDB
import com.softeng.ooyoo.helpers.longToast
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_profile.*

const val USER_PROFILE_CURRENT_EXTRA_NAME = "user profile current extra name"
const val USER_PROFILE_OTHER_EXTRA_NAME = "user profile other extra name"

class ProfileActivity : AppCompatActivity() {

    private var myUid :String = ""
    private val chatDB = ChatDB()
    private lateinit var currentUser: User
    private lateinit var otherUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_profile)
        super.onCreate(savedInstanceState)

        myUid = FirebaseAuth.getInstance().currentUser?.uid.toString()

        currentUser = intent.getParcelableExtra(USER_PROFILE_CURRENT_EXTRA_NAME) ?: User(uid = "_")
        otherUser = intent.getParcelableExtra(USER_PROFILE_OTHER_EXTRA_NAME) ?: User(uid = "_")

        Glide.with(this).load("").placeholder(R.drawable.logo).circleCrop().into(otherUserProfileImageView)

        if(otherUser.uid == "_" || currentUser.uid == "_") {
            longToast("There was an error while opening this profile. Please try again.")
            finish()
        }

        activity_profile_name.text = otherUser.username


        others_profile_reputation_button.setOnClickListener {
            val intent = Intent(this, OthersReputationActivity::class.java)
            intent.putExtra(USER_REPUTATION_OTHER_EXTRA_NAME, currentUser)
            intent.putExtra(USER_REPUTATION_OTHER_EXTRA_NAME, otherUser)
            startActivity(intent)
        }

        others_profile_send_message_button.setOnClickListener {
            chatDB.startChat(currentUser.uid, otherUser.uid,
                onCreateChat = ::openChat,
                onAlreadyExists = ::openChat,
                onFailure = {
                    toast("An error occurred while opening this chat")
                }
            )
        }

        others_profile_see_info_and_bio_button.setOnClickListener {
            toast("This feature is not implemented yet")
        }

        others_profile_send_request.setOnClickListener{
            //TODO(not implemented yet)
        }

        others_profile_block_button.setOnClickListener {
            //TODO(add on DB)
            toast("You have blocked " + otherUser.username)
        }

        profileActivityBackButton.setOnClickListener {
            onBackPressed()
        }

    }

    private fun openChat(chat: Chat){
        val intent= Intent(this, ChatActivity::class.java)

        intent.putExtra(CHAT_SENDER_EXTRA_NAME, currentUser)
        intent.putExtra(USER_CHAT_EXTRA_NAME, otherUser)
        intent.putExtra(CHAT_EXTRA_NAME, chat)

        if(otherUser.uid != currentUser.uid) {
            startActivity(intent)
        }
        else{
            toast("You cannot start a chat with yourself.")
        }
    }
}