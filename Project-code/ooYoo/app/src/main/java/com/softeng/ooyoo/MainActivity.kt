package com.softeng.ooyoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.helpers.USERS
import com.softeng.ooyoo.signUpLogIn.*
import com.softeng.ooyoo.user.User

class MainActivity : AppCompatActivity() {

    private var user: User? = null
    private var uid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(intent != null){
            uid = intent.getStringExtra(USER_EXTRA_NAME) ?: ""
        }

    }

    override fun onStart() {
        super.onStart()
        val auth = FirebaseAuth.getInstance()
        if (auth.uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        else{
            uid = auth.uid!!
            getUser()
        }
    }

    private fun getUser(){
        val db = FirebaseFirestore.getInstance()

        db.collection(USERS)
            .document(uid)
            .get()
            .addOnSuccessListener {document ->
                if(document == null){
                    return@addOnSuccessListener
                }

                user = document.toObject(User::class.java)
            }
    }
}
