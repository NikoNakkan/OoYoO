package othersProfile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.softeng.ooyoo.R

import kotlinx.android.synthetic.main.activity_others_reputation.*
import kotlinx.android.synthetic.main.activity_profile_activiy.*
import kotlinx.android.synthetic.main.activity_reviews.*

class OthersReputationActivity : AppCompatActivity() {
    var userProfileImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_reputation)
        setSupportActionBar(toolbar)

        leave_your_own_review_button.setOnClickListener {
            val intent_leave_review = Intent(this,MakeAReviewActivity::class.java)
            startActivity(intent_leave_review)

        }
    }

}
