package com.softeng.ooyoo.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.toast
import kotlinx.android.synthetic.main.activity_my_reputation.*

const val REPUTATION_USER_EXTRA_NAME = "reputation user extra name"

/**
 * This activity represents the GUI from which the user can see his reviews and rating.
 */
class MyReputationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_reputation)

        val user = intent.getParcelableExtra(REPUTATION_USER_EXTRA_NAME) ?: User(uid="_")

        if (user.uid == "_") {
            toast("An error occurred while loading your data. Please try again.")
            finish()
        }

        reputationBackButton.setOnClickListener {
            onBackPressed()
        }

        Glide.with(this).load(R.drawable.logo).centerCrop().circleCrop().into(reputationUserImage)

        reputationNameTextView.text = user.fullName

        val ratings = user.userRating

        reputationRating.text = (ratings.map { it.stars }.reduce { acc, rating -> acc + rating } / ratings.size).toString()
        reputationRating.append("/5.0")

        reputationReviewRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MyReputationActivity)
            setHasFixedSize(true)
            adapter = ReviewAdapter(this@MyReputationActivity, user.userRating)
        }
    }
}
