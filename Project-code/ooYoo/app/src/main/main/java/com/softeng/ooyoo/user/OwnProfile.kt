package com.softeng.ooyoo.user

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.softeng.ooyoo.R

class OwnProfile : AppCompatActivity() {


        override fun onCreate(savedInstanceState: Bundle?) {
            var userProfileImageView: ImageView? = null

            setContentView(R.layout.activity_own_profile)

            super.onCreate(savedInstanceState)
            userProfileImageView = findViewById<ImageView>(R.id.activity_profile_image)
            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.thic)
            val roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(resources, bitmap)
            roundedBitmapDrawable.isCircular = true
            userProfileImageView!!.setImageDrawable(roundedBitmapDrawable)
        }

    }

