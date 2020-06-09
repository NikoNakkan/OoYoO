package com.softeng.ooyoo.host

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.HostingDB
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
import kotlinx.android.synthetic.main.activity_next_form.*
import kotlinx.android.synthetic.main.fragment_become.*
import java.lang.NumberFormatException

const val HOST_PLACE_EXTRA_NAME = "host place extra name"
const val HOST_DATES_EXTRA_NAME ="host dates extra name"

/**
 * This activity represents the GUI from which the user adds information about his home.
 */
class AddHomeInfoActivity : AppCompatActivity() {
    private val house = House()
    private var homeReq = ""

    val uid = FirebaseAuth.getInstance().uid

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_form)

        val place = intent.getParcelableExtra<Place>(HOST_PLACE_EXTRA_NAME)
        val dates = intent.getParcelableExtra<Dates>(HOST_DATES_EXTRA_NAME)

        if(place == null || dates == null){
            toast("An error occurred.")
            finish()
        }


        finishHostForm.setOnClickListener {
            if (uid == null){
                toast("There was an error while authenticating you.")
            }
            else if(sqMeters.text.isEmpty() || numRooms.text.isEmpty() || floor.text.isEmpty() || homeRequirementsEditText.text.isEmpty()){
                toast("Please fill all the fields.")
            }
            else {

                if (!addSpecs()){
                    return@setOnClickListener
                }

                val host = Hosting(
                    uid = uid,
                    place = place!!,
                    dates = dates!!,
                    house = house,
                    homeReq = homeReq
                )

                val builder = AlertDialog.Builder(this)

                builder.setTitle("Are you sure you want to register this Host Form?")
                    .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->
                        acceptHostForm(uid, host)
                    }
                    .setNegativeButton("No") { _: DialogInterface, _: Int ->
                        cancelForm()
                    }
                    .setNeutralButton("Alter") { _: DialogInterface, _: Int ->
                        alterRegistration()
                    }
                val dialog = builder.create()
                dialog.show()

            }
        }

        addApartmentPhotos.setOnClickListener{
            uploadPhotos()
        }

    }

    /**
     * This method is used to add a home's specifications.
     */
    private fun addSpecs(): Boolean{
        val squareMeters: Int
        val rooms: Int
        val numFloors: Int

        try {
            squareMeters = sqMeters.text.toString().toInt()
            rooms = numRooms.text.toString().toInt()
            numFloors = floor.text.toString().toInt()
        }
        catch (e: NumberFormatException){
            toast("Please make sure that the numbers you entered are correct.")
            return false
        }

        homeReq = homeRequirementsEditText.text.toString()

        house.size = squareMeters
        house.floor = numFloors
        house.numOfRooms = rooms

        return true
    }

    /**
     * This method is used to upload photos of the house.
     */
    private fun uploadPhotos(){
        toast("This feature is not implemented yet.")
    }

    /**
     * This method is used to create a hosting experience.
     */
    private fun acceptHostForm(uid: String, host: Hosting){
        val hostingDB = HostingDB()

        hostingDB.hostRegistration(this, host) { id ->
            val userDB = UserDB()
            userDB.uploadHostingOnDatabase(uid, id)

            finish()
        }
    }

    /**
     * This method is used to cancel a host form.
     */
    private fun cancelForm(){
        finish()
    }

    /**
     * This method is used to change things in the host form.
     */
    private fun alterRegistration(){
        val intent = Intent(this, HostFormActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}