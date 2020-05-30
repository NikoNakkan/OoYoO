package com.softeng.ooyoo.user.othersProfile

//import com.softeng.ooyoo.user.USER_PROFILE_EXTRA_NAME
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.Rating
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_make_a_review.*


class MakeAReviewActivity : AppCompatActivity() {
    private lateinit var currentUser: User
    private lateinit var otherUser: User
    private val reviewtextView: TextView = findViewById(R.id.edit_text_review) as TextView
    private val scoretextView: TextView = findViewById(R.id.score_text_veiw) as TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_make_a_review)
        super.onCreate(savedInstanceState)
        val userDB = UserDB()
        var value: Double = 0.0
        val text: String = scoretextView.getText().toString()
        if (!text.isEmpty()) try {
            value = text.toDouble()
            // it means it is double
        } catch (e1: Exception) {
            // this means it is not double
            e1.printStackTrace()
            return
        }
        currentUser = intent.getParcelableExtra(USER_PROFILE_CURRENT_EXTRA_NAME) ?: User(uid = "_")
        otherUser = intent.getParcelableExtra(USER_PROFILE_OTHER_EXTRA_NAME) ?: User(uid = "_")
        var review = Rating()
        review.uid = currentUser.uid
        review.name = currentUser.username
        review.comment = reviewtextView.getText().toString()
        review.stars = value
        make_a_review_button.setOnClickListener(){
            userDB.uploadReviewOnDatabase(currentUser.uid, review)
            toast("Your review is saved")
        }



    }
}
