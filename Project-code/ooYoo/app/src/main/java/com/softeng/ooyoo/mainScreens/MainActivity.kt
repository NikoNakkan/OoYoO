package com.softeng.ooyoo.mainScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.USERS
import com.softeng.ooyoo.signUpLogIn.*
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var user: User? = null
    private var uid: String = ""
    private var selectedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(intent != null){
            uid = intent.getStringExtra(USER_EXTRA_NAME) ?: ""
        }

        selectedFragment = SearchForTravelersFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, selectedFragment!!).commit()


        bottomNavigationView.selectedItemId = R.id.bot_nav_travel

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.bot_nav_chat -> selectedFragment = ChatFragment()
                R.id.bot_nav_travel -> selectedFragment = SearchForTravelersFragment()
                R.id.bot_nav_carpool -> selectedFragment = SearchForCarpoolersFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, selectedFragment!!).commit()
            true
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
