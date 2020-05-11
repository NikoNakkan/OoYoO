package com.softeng.ooyoo.user

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.softeng.ooyoo.R

class ProfileActiviy : AppCompatActivity() {
    var userProfileImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_profile_activiy)

        super.onCreate(savedInstanceState)
        userProfileImageView = findViewById<ImageView>(R.id.activity_profile_image)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.thic)
        val roundedBitmapDrawable =
            RoundedBitmapDrawableFactory.create(resources, bitmap)
        roundedBitmapDrawable.isCircular = true
        userProfileImageView!!.setImageDrawable(roundedBitmapDrawable)
    }
}
