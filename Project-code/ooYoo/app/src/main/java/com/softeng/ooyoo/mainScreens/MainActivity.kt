package com.softeng.ooyoo.mainScreens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.USERS
import com.softeng.ooyoo.signUpLogIn.*
import com.softeng.ooyoo.toast
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
                R.id.bot_nav_chat -> selectedFragment = ChatFragment()
                R.id.bot_nav_travel -> {
                    selectedFragment = SearchForTravelersFragment()
                    (selectedFragment as SearchForTravelersFragment).setUser(user ?: User())
                }
                R.id.bot_nav_become -> selectedFragment = BecomeFragment()
                R.id.bot_nav_profile -> selectedFragment = OwnProfileFragment()
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
                if(selectedFragment is SearchForTravelersFragment){
                    (selectedFragment as SearchForTravelersFragment).setUser(user ?: User())
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
