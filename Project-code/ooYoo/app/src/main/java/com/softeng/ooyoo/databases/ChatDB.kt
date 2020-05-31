package com.softeng.ooyoo.databases

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.softeng.ooyoo.chat.Chat
import com.softeng.ooyoo.chat.Message

/**
 * This class provides communication with the database for anything related with the chat.
 */
class ChatDB: Database(CHATS) {
    private val dbCollection = FirebaseFirestore.getInstance().collection(this.collection)
    private lateinit var chatListener: ListenerRegistration

    public fun sendTravelMessage(senderId: String, receiverId: String){

    }

    public fun sendHostMessage(senderId: String, receiverId: String){

    }

    public fun sendCarpoolingMessage(senderId: String, receiverId: String){

    }

    /**
     * A method to send a specific message.
     */
    public fun sendMessage(chatId: String, message: Message){
        dbCollection.document(chatId)
            .update(
                "messages",
                FieldValue.arrayUnion(message)
            )

        dbCollection.document(chatId)
            .update("lastMessageSent", message.timestamp)

    }

    public fun retrieveContact(uid0: String, uid1: String): ArrayList<Message>{
        TODO("Not implemented yet.")
    }

    /**
     * A listener for new messages in the database.
     */
    public fun addMessageListener(uid: String, onSuccess: (Chat) -> Unit){
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

    /**
     * A method to start a new chat between 2 users if it doesn't exist
     * or to access it if it already exists.
     */
    public fun retrieveChatData(uid0: String, uid1: String, onCreateChat: (Chat) -> Unit, onAlreadyExists: (Chat) -> Unit, onFailure: () -> Unit){
        dbCollection
            .whereArrayContains("uids", uid0)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for(document in querySnapshot.documents) {
                    val chat = document.toObject(Chat::class.java)
                    if(chat != null && uid1 in chat.uids) {
                        chat.setChatId(document.id)
                        onAlreadyExists(chat)
                        return@addOnSuccessListener
                    }
                }

                val chat = Chat(uids = arrayListOf(uid0, uid1))
                dbCollection.add(chat)
                    .addOnSuccessListener { document ->
                        chat.setChatId(document.id)
                        onCreateChat(chat)
                    }
                    .addOnFailureListener { e ->
                        Log.e(ChatDB::class.java.simpleName, "Error while retrieving chat.", e)
                        onFailure()
                    }
            }
            .addOnFailureListener { e ->
                Log.e(ChatDB::class.java.simpleName, "Error while creating chat.", e)
                onFailure()
            }
    }

    /**
     * A method to detach the listener created from messageListener.
     */
    public fun detachListener(){
        chatListener.remove()
    }

}
