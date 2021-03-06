package com.softeng.ooyoo.signUpLogIn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_signup_add_info.*
import kotlin.collections.ArrayList

/**
 * This activity represents the GUI through which the user signs up.
 * This is the second of the 3 screens used for sign up,
 * in which the user adds his basic information.
 */
class SignupAddInfoActivity : AppCompatActivity() {

    private lateinit var user: User
    private var age = false
    private var livingIn = false
    private var languagesSpeaking = false
    private var gender = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_add_info)

        user = intent.getParcelableExtra(USER_EXTRA_NAME) ?: User(uid="_")
        val password = intent.getStringExtra(PASSWORD_EXTRA_NAME)
        if (user.uid == "_"){
            longToast("There was an error while processing your data. Please try again.")
            finish()
            return
        }

        infoBackButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            finish()
        }

        ageRelativeAddInfoSignup.setOnClickListener {
            addDate(this, startYear=2000){ date ->
                user.age = date
                toast("Age set!")
                age = true
            }
        }

        livingInRelativeSignupInfoButton.setOnClickListener {
            addLocation(supportFragmentManager) { country ->
                user.livingIn = country
                toast("Country set!")
                livingIn = true
            }
        }

        languagesSpeakingSignupInfoButton.setOnClickListener {
            val dialog = LanguageDialog()
            dialog.show(supportFragmentManager, "languages")
        }

        genderSignupInfoButton.setOnClickListener {
            val dialog = GenderDialog()
            dialog.show(supportFragmentManager, "gender")
        }

        infoNextButton.setOnClickListener {
            if (age && livingIn && languagesSpeaking && gender){
                val intent = Intent(this, SignupInterestsActivity::class.java)
                intent.putExtra(USER_EXTRA_NAME, user)
                intent.putExtra(PASSWORD_EXTRA_NAME, password)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
            else{
                longToast("Not all data are filled. Please fill the rest of the data.")
            }
        }

        infoBackButton.setOnClickListener {
            onBackPressed()
        }

    }

    fun updateUserLanguages(languages: ArrayList<Int>){
        for (i in languages){
            user.languages.add(LANGUAGE_ARRAY[i])
            toast("Languages set!")
            languagesSpeaking = true
        }
    }

    fun updateUserGender(genderSelected: String) {
        user.gender = genderSelected
        toast("Gender set!")
        gender = true
    }

}
