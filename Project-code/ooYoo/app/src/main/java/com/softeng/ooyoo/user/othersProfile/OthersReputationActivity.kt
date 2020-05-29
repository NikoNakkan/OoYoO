package com.softeng.ooyoo.user.othersProfile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.softeng.ooyoo.R

import kotlinx.android.synthetic.main.activity_others_reputation.*

const val USER_REPUTATION_CURRENT_EXTRA_NAME = "user reputation current extra name"
const val USER_REPUTATION_OTHER_EXTRA_NAME ="user reputation other extra name"

class OthersReputationActivity : AppCompatActivity() {
    var userProfileImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_reputation)

        leaveYourOwnReviewButton.setOnClickListener {
            val intent = Intent(this, MakeAReviewActivity::class.java)
            startActivity(intent)
        }
    }

}
