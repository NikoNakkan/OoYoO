package com.softeng.ooyoo.databases

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.softeng.ooyoo.chat.Chat
import com.softeng.ooyoo.chat.Message

class ChatDB: Database(CHATS) {
    val dbCollection = FirebaseFirestore.getInstance().collection(this.collection)
    private lateinit var chatListener: ListenerRegistration

    public fun sendTravelMessage(senderId: String, receiverId: String){

    }

    public fun sendHostMessage(senderId: String, receiverId: String){

    }

    public fun sendCarpoolingMessage(senderId: String, receiverId: String){

    }


    public fun sendMessage(chatId: String, message: Message){
        dbCollection.document(chatId)
            .update(
                "messages",
                FieldValue.arrayUnion(message)
            )

        dbCollection.document(chatId)
            .update("lastMessageSent", message.timestamp)

    }



    public fun retrieveChatData(chatIds: ArrayList<String>, onSuccess: (ArrayList<Chat>) -> Unit){
        dbCollection
            .whereIn(FieldPath.documentId(), chatIds)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val chats = arrayListOf<Chat>()
                val ids = arrayListOf<String>()

                for (document in querySnapshot){
                    val chat = document.toObject(Chat::class.java)
                    chat.setChatId(document.id)
                    chats.add(chat)
                }

                onSuccess(chats)
            }
    }

    public fun retrieveContact(uid0: String, uid1: String): ArrayList<Message>{

        return arrayListOf()
    }

    public fun messageListener(uid: String, onSuccess: (Chat) -> Unit){

        chatListener = dbCollection
            .whereArrayContains("uids", uid)
            .addSnapshotListener { querySnapshot, e ->
                if(e != null || querySnapshot == null){
                    Log.e(ChatDB::class.java.simpleName, "Error while retrieving chat messages", e)
                }

                for (document in querySnapshot!!.documents){

                    val messageArrayMap = (document?.data?.get("messages") as ArrayList<HashMap<*, *>>?)
                        ?: return@addSnapshotListener

                    val uids = (document?.data?.get("uids") as ArrayList<String>?)
                        ?: return@addSnapshotListener

                    val lastMessageSent = (document?.data?.get("lastMessageSent") as Timestamp?)
                        ?: return@addSnapshotListener

                    val messages = arrayListOf<Message>()

                    for(messageMap: HashMap<*, *> in messageArrayMap){
                        messages.add(Message(messageMap))
                    }

                    messages.sortedByDescending {
                        it.timestamp
                    }

                    val chat = Chat(uids, messages, lastMessageSent)
                    chat.setChatId(document.id)

                    onSuccess(chat)
                }

            }
    }

    public fun detachListener(){
        chatListener.remove()
    }
}
