package com.softeng.ooyoo.user.othersProfile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.longToast
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.user.ReviewAdapter
import com.softeng.ooyoo.user.User

import kotlinx.android.synthetic.main.activity_others_reputation.*

const val USER_REPUTATION_CURRENT_EXTRA_NAME = "user reputation current extra name"
const val USER_REPUTATION_OTHER_EXTRA_NAME ="user reputation other extra name"

/**
 * This activity represents the GUI from which the user can see another user's reviews and rating.
 */
class OthersReputationActivity : AppCompatActivity() {
    var userProfileImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_reputation)

        val currentUser = intent.getParcelableExtra(USER_REPUTATION_CURRENT_EXTRA_NAME) ?: User(uid = "_")
        val otherUser = intent.getParcelableExtra(USER_REPUTATION_OTHER_EXTRA_NAME) ?: User(uid = "_")

        if(otherUser.uid == "_" || currentUser.uid == "_") {
            toast("There was an error while opening this profile. Please try again.")
            finish()
        }

        Glide.with(this).load(R.drawable.logo).circleCrop().into(activityOtherUserReviewsUserImage)

        activityOtherUserReviewsUserName.text = otherUser.fullName

        val ratings = otherUser.userRating
        if(ratings.size > 0) {
            starsReviewActivity.text = (ratings.map { it.stars }
                .reduce { acc, rating -> acc + rating } / ratings.size).toString()
            starsReviewActivity.append("/5.0")
        }

        otherUserReviewsRecycler.apply {
            layoutManager = LinearLayoutManager(this@OthersReputationActivity)
            setHasFixedSize(true)
            adapter = ReviewAdapter(this@OthersReputationActivity, otherUser.userRating)
        }

        leaveYourOwnReviewButton.setOnClickListener {
            val intent = Intent(this, MakeAReviewActivity::class.java)
            intent.putExtra(USER_MAKE_REVIEW_CURRENT_EXTRA_NAME, currentUser)
            intent.putExtra(USER_MAKE_REVIEW_OTHER_EXTRA_NAME, otherUser)
            startActivity(intent)
        }
    }

}
