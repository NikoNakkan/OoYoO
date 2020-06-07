package com.softeng.ooyoo.user.othersProfile

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.carpool.Carpooling
import com.softeng.ooyoo.chat.*
import com.softeng.ooyoo.databases.ChatDB
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.travel.TravelEvent
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_profile.*
import java.util.*

const val USER_PROFILE_CURRENT_EXTRA_NAME = "user profile current extra name"
const val USER_PROFILE_OTHER_EXTRA_NAME = "user profile other extra name"
const val USER_PROFILE_EVENT_EXTRA_NAME = "user profile event extra name"

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
        val travelEvent = intent.getParcelableExtra<TravelEvent>(USER_PROFILE_EVENT_EXTRA_NAME)


        if(otherUser.uid == "_" || currentUser.uid == "_") {
            toast("There was an error while opening this profile. Please try again.")
            finish()
        }

        if(travelEvent == null){
            othersProfileSendRequest.isEnabled = false
        }

        if(currentUser.uid == otherUser.uid){
            toast("This is your profile.")
            finish()
        }


        val category = when (travelEvent) {
            is Hosting -> HOSTINGS_CAT
            is Carpooling -> CARPOOLINGS_CAT
            else -> TRIP_PLAN_CAT
        }


        Glide.with(this).load("").placeholder(R.drawable.logo).circleCrop().into(otherUserProfileImageView)

        activityProfileName.text = otherUser.username


        othersProfileReputationButton.setOnClickListener {
            val intent = Intent(this, OthersReputationActivity::class.java)
            intent.putExtra(USER_REPUTATION_CURRENT_EXTRA_NAME, currentUser)
            intent.putExtra(USER_REPUTATION_OTHER_EXTRA_NAME, otherUser)
            startActivity(intent)
        }

        othersProfileSendMessageButton.setOnClickListener {
            chatDB.retrieveChatData(currentUser.uid, otherUser.uid,
                onCreateChat = ::openChat,
                onAlreadyExists = ::openChat,
                onFailure = {
                    toast("An error occurred while opening this chat")
                }
            )
        }

        othersProfileSeeInfoAndBioButton.setOnClickListener {
            toast("This feature is not implemented yet")
        }


        when(category){
            TRIP_PLAN_CAT -> othersProfileSendRequest.text = "Send Travel Request"
            HOSTINGS_CAT -> othersProfileSendRequest.text = "Send Hosting Request"
            CARPOOLINGS_CAT -> othersProfileSendRequest.text = "Send Carpooling Request"
        }

        othersProfileSendRequest.setOnClickListener{
            AsyncTask.execute {
                val date = Date(getTime())

                val country = travelEvent!!.place.name
                val startDate = dateMapToString(travelEvent.dates.startDate)
                val endDate = dateMapToString(travelEvent.dates.endDate)

                val text = when(travelEvent){
                    is Hosting -> {
                        "Hey, I'll be at $country from $startDate to $endDate!\nCould you host me?"
                    }
                    is Carpooling -> {
                        "Hey, I'll be going to $country at $startDate!\nCould you drive me there?"
                    }
                    else -> {
                        "Hey, I'll be going to $country from $startDate to $endDate!\nDo you want to meet there?"
                    }
                }

                val message = Message(currentUser.uid, otherUser.uid, text, Timestamp(date))

                chatDB.retrieveChatData(
                    currentUser.uid ,
                    otherUser.uid,
                    onAlreadyExists = {chat ->
                        sendRequest(chat.getChatId(), message)
                    },
                    onCreateChat = {chat ->
                        sendRequest(chat.getChatId(), message)
                    },
                    onFailure = {
                        toast("There was a problem sending this request. Please try again.")
                    })
            }
        }

        othersProfileBlockButton.setOnClickListener {
            toast("This feature is not implemented yet.")
        }

        profileActivityBackButton.setOnClickListener {
            onBackPressed()
        }

    }

    /**
     * This is a method used to open the chat screen for 2 users.
     */
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

    /**
     * This is a method used to send a travel request to another user.
     */
    private fun sendRequest(chatId: String, message: Message){
        chatDB.sendMessage(chatId, message)
        toast("Your request was sent successfully!")
        finish()
    }
}

