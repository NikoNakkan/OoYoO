package com.softeng.ooyoo.signUpLogIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.softeng.ooyoo.R
import com.softeng.ooyoo.helpers.INTERESTS_ARRAY_LIST
import com.softeng.ooyoo.databases.USERS
import com.softeng.ooyoo.longToast
import com.softeng.ooyoo.toast
import com.softeng.ooyoo.user.User
import kotlinx.android.synthetic.main.activity_signup_interests.*

class SignupInterestsActivity : AppCompatActivity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_interests)

        user = intent.getParcelableExtra(USER_EXTRA_NAME) ?: User(uid="_")
        val password = intent.getStringExtra(PASSWORD_EXTRA_NAME)

        if (user.uid == "_" || password == null){
            longToast("There was an error while processing your data. Please try again.")
            finish()
            return
        }

        val layoutManager = GridLayoutManager(this, 3)
        val adapter = InterestsAdapter(this, INTERESTS_ARRAY_LIST)

        signupInterestsRecylcerView.apply {
            this.layoutManager = layoutManager
            this.adapter = adapter
            setHasFixedSize(true)
        }

        interestsFinishTextView.setOnClickListener {
            if(user.interests.size == 0){
                toast("Please select at least 1 interest")
            }
            else{
                val auth = FirebaseAuth.getInstance()
                val db = FirebaseFirestore.getInstance()

                auth.createUserWithEmailAndPassword(user.email, password)
                    .addOnSuccessListener {
                        if (auth.uid != null) {
                            user.uid = auth.uid ?: ""
                            db.collection(USERS)
                                .document(auth.uid!!)
                                .set(user)
                                .addOnFailureListener { e ->
                                    Log.e(SignupInterestsActivity::class.java.simpleName, "Error on sign up", e)
                                }
                        }
                        finish()
                    }
            }
        }

        interestsBackButton.setOnClickListener {
            onBackPressed()
        }

    }

    fun getHasInterest(interest: String): Boolean = user.interests.contains(interest)

    fun setHasInterest(interest: String, value: Boolean){

        if(value){
            user.interests.add(interest)
        }
        else{
            user.interests.remove(interest)
        }
    }
}
