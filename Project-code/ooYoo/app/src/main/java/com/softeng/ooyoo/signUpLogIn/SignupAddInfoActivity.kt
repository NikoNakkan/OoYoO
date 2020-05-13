package com.softeng.ooyoo.signUpLogIn

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.LANGUAGE_ARRAY
import com.softeng.ooyoo.longToast
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.user.User
import com.ybs.countrypicker.CountryPicker
import kotlinx.android.synthetic.main.activity_signup_add_info.*
import java.util.*
import kotlin.collections.ArrayList

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

//        val dateListener = DatePicker.OnDateChangedListener()
        ageRelativeAddInfoSignup.setOnClickListener{
            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, year: Int, month: Int, day: Int ->
                    val cal = Calendar.getInstance()
                    cal[Calendar.YEAR] = year
                    cal[Calendar.MONTH] = month
                    cal[Calendar.DAY_OF_MONTH] = day
                    user.age = cal
                    toast("Age set!")
                    age = true
                },
                2000,
                0,
                1)

            datePickerDialog.show()
        }

        livingInRelativeSignupInfoButton.setOnClickListener {
            val countryPicker = CountryPicker.newInstance("Select Country")
            countryPicker.setListener{ name: String, _: String, _: String, _: Int ->
                user.livingIn = name
                toast("Country set!")
                livingIn = true
                countryPicker.dismiss()
            }

            countryPicker.show(supportFragmentManager, "COUNTRY_PICKER")
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
