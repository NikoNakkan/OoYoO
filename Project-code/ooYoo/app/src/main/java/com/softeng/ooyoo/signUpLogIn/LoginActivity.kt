package com.softeng.ooyoo.signUpLogIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.softeng.ooyoo.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val activityContext = this
        goToSignUpButton.setOnClickListener {
            val intent = Intent(activityContext, SignupActivity::class.java)
            activityContext.startActivity(intent)
        }
    }
}