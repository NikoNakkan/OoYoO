package com.softeng.ooyoo.chat

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.ChatDB
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

const val USER_CHAT_EXTRA_NAME = "user chat extra name"
const val CHAT_EXTRA_NAME = "chat extra name"

class ChatActivity : AppCompatActivity() {

    private val chatDB = ChatDB()

//    @ServerTimestamp
//    val time



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val uid = FirebaseAuth.getInstance().uid
        val receiver = intent.getParcelableExtra(USER_CHAT_EXTRA_NAME) ?: User(uid="_")
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
        val messageAdapter = MessageAdapter(this, chat.messages, uid!!, receiver)
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
//                    Log.d(ChatActivity::class.java.simpleName, getTime().toString())
                    val date = Date(getTime())
                    val message = Message(uid!!, receiver.uid, text, Timestamp(date))
                    chatDB.sendMessage(chat.getChatId(), message)
                })


                chatMessageEditText.setText("")
                chatActivityRecyclerView.scrollToPosition(messageAdapter.itemCount)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        chatDB.detachListener()
    }

    private fun ArrayList<Message>.containsMessage(newMessage: Message): Boolean{
        for(message in this){
            if(message.timestamp == newMessage.timestamp){
                return true
            }
        }
        return false
    }

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
