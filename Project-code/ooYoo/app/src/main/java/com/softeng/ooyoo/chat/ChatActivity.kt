package com.softeng.ooyoo.chat

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.ChatDB
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.user.User
import com.softeng.ooyoo.user.othersProfile.ProfileActivity
import com.softeng.ooyoo.user.othersProfile.USER_PROFILE_CURRENT_EXTRA_NAME
import com.softeng.ooyoo.user.othersProfile.USER_PROFILE_OTHER_EXTRA_NAME
import kotlinx.android.synthetic.main.activity_chat.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

const val CHAT_SENDER_EXTRA_NAME = "chat sender extra name"
const val USER_CHAT_EXTRA_NAME = "user chat extra name"
const val CHAT_EXTRA_NAME = "chat extra name"

/**
 * This activity represents the GUI for a chat between 2 users (it included their messages).
 */
class ChatActivity : AppCompatActivity() {

    private val chatDB = ChatDB()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val uid = FirebaseAuth.getInstance().uid
        val sender = intent.getParcelableExtra(CHAT_SENDER_EXTRA_NAME) ?: User(uid = "_")
        val receiver = intent.getParcelableExtra(USER_CHAT_EXTRA_NAME) ?: User(uid = "_")
        val chat = intent.getParcelableExtra<Chat>(CHAT_EXTRA_NAME)
        val imageUrl = ""

        if(receiver.uid == "_" || chat == null || uid == null){
            toast("There was an error while opening this chat.")
            finish()
        }

        Glide.with(this).load(imageUrl).placeholder(R.drawable.logo).circleCrop().into(chatActivityProfileImageView)

        chatActivityNameTextView.text = receiver.fullName.split(" ")[0]

        chatBackButton.setOnClickListener {
            onBackPressed()
        }


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true

        chatActivityRecyclerView.layoutManager = linearLayoutManager
        chatActivityRecyclerView.setHasFixedSize(true)
        val messageAdapter = MessageAdapter(this, chat!!.messages, uid!!, receiver)
        chatActivityRecyclerView.adapter = messageAdapter
        chatActivityRecyclerView.scrollToPosition(messageAdapter.itemCount-1)

        chatDB.messageListener(uid){ newChat ->
            val messages = newChat.messages

            for (message in messages){
                if((message.senderId == uid && message.receiverId == receiver.uid) || (message.receiverId == uid && message.senderId == receiver.uid)){

                    if(!chat.messages.containsMessage(message)){
                        chat.messages.add(message)
                        messageAdapter.notifyItemInserted(chat.messages.size-1)
                    }

                }
            }
        }

        chatSendButton.setOnClickListener {
            if(chatMessageEditText.text.toString() != "") {
                val text = chatMessageEditText.text.toString()
                AsyncTask.execute(kotlinx.coroutines.Runnable {
                    val date = Date(getTime())
                    val message = Message(uid, receiver.uid, text, Timestamp(date))
                    chatDB.sendMessage(chat.getChatId(), message)
                })


                chatMessageEditText.setText("")
                chatActivityRecyclerView.scrollToPosition(messageAdapter.itemCount)
            }
        }

        chatInfoLinearLayout.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(USER_PROFILE_CURRENT_EXTRA_NAME, sender)
            intent.putExtra(USER_PROFILE_OTHER_EXTRA_NAME, receiver)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        chatDB.detachListener()
    }

    /**
     * Checks if an arraylist of messages contains a specific message.
     */
    private fun ArrayList<Message>.containsMessage(newMessage: Message): Boolean{
        for(message in this){
            if(message.timestamp == newMessage.timestamp){
                return true
            }
        }
        return false
    }

    /**
     * Gets the time from a server to ensure that the messages' order will not be impacted by the
     * time differences in the devices.
     */
    @Throws(Exception::class)
    private fun getTime(): Long {
        val url = "https://time.is/Unix_time_now"
        val doc: Document = Jsoup.parse(URL(url).openStream(), "UTF-8", url)
        val tags = arrayOf(
            "div[id=time_section]",
            "div[id=clock0_bg]"
        )
        var elements: Elements = doc.select(tags[0])
        for (i in tags.indices) {
            elements = elements.select(tags[i])
        }
        return (elements.text().toString() + "000").toLong()
    }
}
