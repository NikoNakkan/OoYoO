package com.softeng.ooyoo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.signUpLogIn.LoginActivity
import com.softeng.ooyoo.signUpLogIn.SignupActivity
import com.softeng.ooyoo.signUpLogIn.SignupAddInfoActivity
import com.softeng.ooyoo.signUpLogIn.SignupInterestsActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().uid == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}

