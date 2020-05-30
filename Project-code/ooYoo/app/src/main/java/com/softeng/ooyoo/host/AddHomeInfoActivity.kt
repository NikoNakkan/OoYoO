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
import kotlinx.android.synthetic.main.activity_add_home_info.*
import java.lang.NumberFormatException

const val HOST_PLACE_EXTRA_NAME = "host place extra name"
const val HOST_DATES_EXTRA_NAME ="host dates extra name"

/**
 * This activity represents the GUI from which the user adds information about his home.
 */
class AddHomeInfoActivity : AppCompatActivity() {
    private val house = House()

    val uid = FirebaseAuth.getInstance().uid

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_home_info)

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
            else if(sqMeters.text.isEmpty() || numRooms.text.isEmpty() || floor.text.isEmpty() || homeSpecificationEditText.text.isEmpty()){
                toast("Please fill all the fields.")
            }
            else {

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
                    return@setOnClickListener
                }

                val homeSpec = homeSpecificationEditText.text.toString()

                house.size = squareMeters
                house.floor = numFloors
                house.numOfRooms = rooms


                val host = Hosting(
                    uid = uid,
                    place = place!!,
                    dates = dates!!,
                    house = house,
                    addHomeReq = homeSpec
                )

                val builder = AlertDialog.Builder(this)

                builder.setTitle("Are you sure you want to register this Host Form?")
                    .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->

                        val hostingDB = HostingDB()

                        hostingDB.hostRegistration(this, host) { id ->
                            val userDB = UserDB()
                            userDB.uploadHostingOnDatabase(uid, id)

                            finish()
                        }

                    }
                    .setNegativeButton("No") { _: DialogInterface, _: Int ->
                        finish()
                    }
                    .setNeutralButton("Alter") { _: DialogInterface, _: Int ->
                        val intent = Intent(this, BecomeHostActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()
                    }
                val dialog = builder.create()
                dialog.show()

            }
        }

        addApartmentPhotos.setOnClickListener{
            toast("This feature is not implemented yet.")
        }

    }
}