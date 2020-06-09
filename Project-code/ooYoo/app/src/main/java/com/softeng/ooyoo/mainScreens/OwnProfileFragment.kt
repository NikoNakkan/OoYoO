package com.softeng.ooyoo.mainScreens

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.HostingDB
import com.softeng.ooyoo.databases.TripPlansDB
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.*
import com.softeng.ooyoo.host.Hosting
import com.softeng.ooyoo.signUpLogIn.LoginActivity
import com.softeng.ooyoo.trip.TripPlan
import com.softeng.ooyoo.user.*


/**
 * This fragment represents the GUI from which a user can see his profile.
 */
class OwnProfileFragment : Fragment(), PassUser {

    private lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_own_profile, container, false)

        val nameTextView = view.findViewById<TextView>(R.id.activityProfileName)
        val deleteButton = view.findViewById<TextView>(R.id.own_profile_delete_account)
        val tripsHostsButton = view.findViewById<TextView>(R.id.own_profile_edit_trips_and_hosts_button)
        val editInfoButton = view.findViewById<TextView>(R.id.own_profile_edit_info_and_bio)
        val reputationButton = view.findViewById<TextView>(R.id.own_profile_reputation_button)
        val activityProfileImage = view.findViewById<ImageView>(R.id.otherUserProfileImageView)

        nameTextView.text = user.fullName

        Glide.with(this).load("").placeholder(R.drawable.logo).circleCrop().into(activityProfileImage)

        activityProfileImage.setOnClickListener {
            context?.toast("This feature is not implemented yet.")
        }

        reputationButton.setOnClickListener {
            checkReputation()
        }

        tripsHostsButton.setOnClickListener {
            pickCategory { category ->
                when(category){
                    TRIP_PLAN_CAT -> {
                        editMyTripList()
                    }
                    HOSTINGS_CAT -> {
                        editMyHostingList()
                    }
                    CARPOOLINGS_CAT -> {
                        editMyCarpoolingList()
                    }
                }
            }
        }

        editInfoButton.setOnClickListener {
            editBioActivityClicked()
        }

        deleteButton.setOnClickListener {
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            val uid = user.uid

            deleteDialogClicked(firebaseUser, uid)
        }

        return view
    }

    /**
     * Set the current user.
     */
    override fun setUser(user: User){
        this.user = user
    }

    /**
     * This method creates a dialog to pick a travel event.
     */
    private fun pickCategory(onPick: (Int) -> Unit){
        val builder = AlertDialog.Builder(context)

        builder.setTitle("What would you like to see?")
            .setItems(arrayOf("Trip Plans", "Hosts", "Carpoolings")) { _: DialogInterface, which: Int ->
                onPick(which)
            }
        builder.create().show()
    }

    /**
     * This method retrieves the user's TripPlans.
     */
    private fun editMyTripList(){
        val tripPlansDB = TripPlansDB()
        tripPlansDB.getMyTripList(
            user.uid,
            onSuccess = ::returnTrips,
            onFailure = {
                context?.longToast("There was an error, while retrieving your trips. Pleas try again.")
            })
    }

    /**
     * This method opens a list with the user's TripPlans.
     */
    private fun returnTrips(tripPlanList: ArrayList<TripPlan>){
        val intent = Intent(activity, MyTripPlansListActivity::class.java)
        intent.putParcelableArrayListExtra(TRIPS_LIST_EXTRA_NAME, tripPlanList)
        startActivity(intent)
    }

    /**
     * This method retrieves the user's Hostings.
     */
    private fun editMyHostingList(){
        val hostingDB = HostingDB()
        hostingDB.getMyHostList(
            user.uid,
            onSuccess = ::returnHostings,
            onFailure = {
                context?.longToast("There was an error, while retrieving your hosts. Pleas try again.")
            })
    }

    /**
     * This method opens a list with the user's Hostings.
     */
    private fun returnHostings(hostingList: ArrayList<Hosting>){
        val intent = Intent(activity, MyHostingsListActivity::class.java)
        intent.putParcelableArrayListExtra(HOSTS_LIST_EXTRA_NAME, hostingList)
        startActivity(intent)
    }

    /**
     * This method opens a list of the current user's hostings.
     * (It is not implemented yet.)
     */
    private fun editMyCarpoolingList(){
        context?.toast("This feature is not implemented yet.")
    }


    private fun editBioActivityClicked(){
        context?.toast("This feature is not implemented yet.")
    }

    /**
     * This method opens an activity to see the user's reviews.
     */
    private fun checkReputation(){
        val intent = Intent(context, MyReputationActivity::class.java)
        intent.putExtra(REPUTATION_USER_EXTRA_NAME, user)
        context?.startActivity(intent)
    }

    /**
     * This method opens a dialog to delete the current account.
     */
    private fun deleteDialogClicked(firebaseUser: FirebaseUser?, uid: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Are you sure you want to delete your account?")
            .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                deleteAccount(firebaseUser, uid)
            }
            .setNegativeButton("No"){ _: DialogInterface, _: Int ->

            }
        builder.create().show()
    }

    /**
     * This method deletes the user's account
     */
    private fun deleteAccount(firebaseUser: FirebaseUser?, uid: String){
        firebaseUser?.delete()
            ?.addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    Log.d(OwnProfileFragment::class.java.simpleName, "User account delete")

                    val userDB = UserDB()
                    userDB.deleteAccount(uid)

                    signupRedirect()

                }
                else {
                    Log.d(OwnProfileFragment::class.java.simpleName,"Deleting user account failed")
                    context?.longToast("There was an error while deleting your account. Please try again.")
                }
            }
            ?: context?.longToast("There was an error while deleting your account. Please try again.")

    }

    /**
     * This method redirects the user to the sign up activity when his account is deleted.
     */
    private fun signupRedirect(){
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }

}
