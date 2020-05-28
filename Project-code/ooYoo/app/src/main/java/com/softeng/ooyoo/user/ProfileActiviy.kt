package com.softeng.ooyoo.user

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.chat.CHAT_EXTRA_NAME
import com.softeng.ooyoo.chat.ChatActivity
import com.softeng.ooyoo.databases.ChatDB
import com.softeng.ooyoo.chat.USER_CHAT_EXTRA_NAME
import com.softeng.ooyoo.chat.ChatListAdapter
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.mainScreens.MainActivity
import kotlinx.android.synthetic.main.activity_profile_activiy.*
import othersProfile.OthersReputationActivity

const val USER_PROFILE_EXTRA_NAME ="user profile name extra"
class ProfileActiviy : AppCompatActivity() {
    var userProfileImageView: ImageView? = null
    var myUid :String = ""
    private val chatDB = ChatDB()
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_profile_activiy)

        super.onCreate(savedInstanceState)
        myUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        userProfileImageView = findViewById<ImageView>(R.id.activity_profile_image)
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.thic)
        val roundedBitmapDrawable =
                RoundedBitmapDrawableFactory.create(resources, bitmap)
        roundedBitmapDrawable.isCircular = true
        userProfileImageView!!.setImageDrawable(roundedBitmapDrawable)
        val user = intent.getParcelableExtra<User>(USER_PROFILE_EXTRA_NAME)

        if(user== null) {
            finish()
        }else{
            activity_profile_name.setText(user.username)

            others_profile_reputation_button.setOnClickListener {
                val intent = Intent(this,OthersReputationActivity::class.java)
                startActivity(intent)

            }
            others_profile_send_message_button.setOnClickListener {
                chatDB.messageListener(user.uid ){chat ->
                    if(myUid in chat.uids){
                        val intent= Intent(this ,ChatActivity::class.java)
                        intent.putExtra(USER_CHAT_EXTRA_NAME, user)
                        intent.putExtra(CHAT_EXTRA_NAME, chat )
                        startActivity(intent)
                    }
                }

                toast("LOOK AT ME BITCH")
            }
            others_profile_see_info_and_bio_button.setOnClickListener {
                toast("Feature not yet implemented")

            }
            others_profile_send_request.setOnClickListener(){

            }
            others_profile_block_button.setOnClickListener {
                toast("You have block "+ user.username )
            }
        }

    }
}