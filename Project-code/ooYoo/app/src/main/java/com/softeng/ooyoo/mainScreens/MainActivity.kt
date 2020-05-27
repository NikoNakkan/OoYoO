package com.softeng.ooyoo.mainScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.softeng.ooyoo.R
import com.softeng.ooyoo.chat.Chat
import com.softeng.ooyoo.chat.Message
import com.softeng.ooyoo.databases.CHATS
import com.softeng.ooyoo.databases.ChatDB
import com.softeng.ooyoo.databases.USERS
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.signUpLogIn.*
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var user: User? = null
    private var uid: String = ""
    private var selectedFragment: Fragment? = null
    private var bottomNavigationIsEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if(intent != null){
            uid = intent.getStringExtra(USER_EXTRA_NAME) ?: ""
        }


        selectedFragment = SearchForTravelersFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, selectedFragment!!).commit()



        bottomNavigationView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigationView.selectedItemId = R.id.bot_nav_travel

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            if(!bottomNavigationIsEnabled){
                return@setOnNavigationItemSelectedListener false
            }

            when (item.itemId){
                R.id.bot_nav_chat -> {
                    selectedFragment = ChatFragment()
                    (selectedFragment as ChatFragment).setUser(user ?: User())
                }
                R.id.bot_nav_travel -> {
                    selectedFragment = SearchForTravelersFragment()
                    (selectedFragment as SearchForTravelersFragment).setUser(user ?: User())
                }
                R.id.bot_nav_become -> selectedFragment = BecomeFragment()
                R.id.bot_nav_profile -> {
                    selectedFragment = OwnProfileFragment()
                    (selectedFragment as OwnProfileFragment).setUser(user ?: User())
                }
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
        val userDB = UserDB()

        userDB.retrieveSearchedUser(
            uid,
            onSuccess = { users ->
                user = users[0]

                if(selectedFragment is SearchForTravelersFragment){
                    (selectedFragment as SearchForTravelersFragment).setUser(user ?: User())
                }

            },
            onFailure = {
                toast("There was an error while retrieving your data.")
            }
        )

        userDB.userListener(uid){ newUser ->
            if(newUser != null){
                user = newUser
            }
        }
    }

    fun disableBottomNavigationView(){
        bottomNavigationIsEnabled = false
    }

    fun enableBottomNavigationView(){
        bottomNavigationIsEnabled = true
    }
}
