package com.softeng.ooyoo.signUpLogIn

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.mainScreens.MainActivity
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val activityContext = this

        loginTextView.setOnClickListener {
            if (loginEmailEditText.text.toString() == "" || loginPasswordEditText.text.toString() == "") {
                toast("Please fill your account details")
            }
            else {
                auth.signInWithEmailAndPassword(
                    loginEmailEditText.text.toString(),
                    loginPasswordEditText.text.toString()
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Log.d(LoginActivity::class.java.simpleName,"signInWithEmail:success")
                            logInToApp()
                        }
                        else{
                            Log.e(LoginActivity::class.java.simpleName,"signInWithEmail:failure", task.exception)
                            toast("Authentication failed.")
                        }
                    }
            }
        }


        googleSignInButton.setOnClickListener {
            toast("This feature is not ready yet.")
        }

        forgotPasswordTextView.setOnClickListener {
            toast("This feature is not ready yet.")
        }

        goToSignUpButton.setOnClickListener {
            val intent = Intent(activityContext, SignupActivity::class.java)
            activityContext.startActivity(intent)
            finish()
        }
    }

    private fun logInToApp() {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra(USER_EXTRA_NAME, auth.currentUser?.uid)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(i)
        finish()
    }
}