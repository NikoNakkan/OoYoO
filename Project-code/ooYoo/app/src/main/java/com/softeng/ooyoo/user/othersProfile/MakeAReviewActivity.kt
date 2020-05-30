package com.softeng.ooyoo.user.othersProfile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_make_a_review.*
import java.lang.NumberFormatException

const val USER_MAKE_REVIEW_CURRENT_EXTRA_NAME = "user make review current extra name"
const val USER_MAKE_REVIEW_OTHER_EXTRA_NAME ="user make review other extra name"

/**
 * This activity represents the GUI from which the user adds ar review for another user.
 */
class MakeAReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_make_a_review)
        super.onCreate(savedInstanceState)

        val currentUser = intent.getParcelableExtra(USER_MAKE_REVIEW_CURRENT_EXTRA_NAME) ?: User(uid = "_")
        val otherUser = intent.getParcelableExtra(USER_MAKE_REVIEW_OTHER_EXTRA_NAME) ?: User(uid = "_")

        if(currentUser.uid == "_" || otherUser.uid == "_"){
            toast("There was an error while opening this screen. Please try again.")
            finish()
        }

        val userDb = UserDB()

        makeAReviewButton.setOnClickListener{
            if(scoreEditText.text.isEmpty() || editTextReview.text.isEmpty()){
                toast("Please fill all fields")
                return@setOnClickListener
            }

            val value = try {
                scoreEditText.text.toString().toDouble()
            }
            catch (e: NumberFormatException){
                toast("The score needs to be a valid number.")
                return@setOnClickListener
            }

            if(value < 0 || value > 5){
                toast("The score needs to be between 0 and 5.")
                return@setOnClickListener
            }

            val rating = Rating(currentUser.uid, currentUser.username, "", value, editTextReview.text.toString())

            userDb.uploadReviewOnDatabase(
                otherUser.uid,
                rating,
                onSuccess = {
                    toast("Your review was saved successfully")
                    finish()
                },
                onFailure = {
                    toast("There was an error while saving your review. Please try again.")
                })
        }



    }
}
