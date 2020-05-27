package com.softeng.ooyoo.user

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.app.AlertDialog
import android.content.DialogInterface
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.signUpLogIn.USER_EXTRA_NAME
import kotlinx.android.synthetic.main.activity_profile_activiy.*

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


        val user = intent.getParcelableExtra<User>(USER_EXTRA_NAME)
        if(user== null) {
            finish()
        }else{
            activity_profile_name.setText(user.username)

            others_profile_reputation_button.setOnClickListener {
                val intent_review = Intent(this ,profile_relative_layout::class.java)
                startActivity(intent_review)

            }
            others_profile_send_message_button.setOnClickListener {
                val intent_chat= Intent(this ,profile_relative_layout::class.java)
                startActivity(intent_chat)

            }
            others_profile_see_info_and_bio_button.setOnClickListener {


            }
            others_profile_block_button.setOnClickListener {
                toast("You have block "+ user.username )
            }
        }

    }
}