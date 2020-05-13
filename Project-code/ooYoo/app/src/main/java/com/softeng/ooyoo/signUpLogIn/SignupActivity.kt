package com.softeng.ooyoo.signUpLogIn

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.softeng.ooyoo.R
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.*

const val USER_EXTRA_NAME = "User"
const val PASSWORD_EXTRA_NAME = "Password"

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupNextTextView.setOnClickListener {
            val password = signupPasswordEditText.text.toString()

            if (signupNameAndSurname.text.toString() == ""
                || signupEnterEmailEdittext.text.toString() == ""
                || password == "") {
                toast("Please fill all the info")
            }
            else if (password != reEnterPasswordSignup.text.toString()) {
                toast("Passwords don't match")
            }
            else if (password.length < 6) {
                toast("Password must have at least 6 digits")
            }
            else {
                val user = User(
                    username = signupAddUsername.text.toString(),
                    email = signupEnterEmailEdittext.text.toString(),
                    fullName = signupNameAndSurname.text.toString(),
                    phoneNumber = signupEnterPhoneNumber.text.toString())

                val intent = Intent(this, SignupAddInfoActivity::class.java)
                intent.putExtra(USER_EXTRA_NAME, user)
                intent.putExtra(PASSWORD_EXTRA_NAME, password)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }

        }
    }
}
