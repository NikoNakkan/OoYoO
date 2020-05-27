package com.softeng.ooyoo.user

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.toast

const val USER_PROFILE_EXTRA_NAME = "user profile extra name"

class ProfileActiviy : AppCompatActivity() {
    private lateinit var user: User
    var userProfileImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_profile_activiy)
        super.onCreate(savedInstanceState)


        user = intent.getParcelableExtra(USER_PROFILE_EXTRA_NAME) ?: User(uid="_")

        if (user.uid == "_") {
            toast("There was an error opening this profile. Pleas try again.")
            finish()
        }


        userProfileImageView = findViewById<ImageView>(R.id.activity_profile_image)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.thic)
        val roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
        roundedBitmapDrawable.isCircular = true
        userProfileImageView!!.setImageDrawable(roundedBitmapDrawable)
    }
}
