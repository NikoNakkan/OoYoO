package com.softeng.ooyoo.host

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.HostingDB
import com.softeng.ooyoo.helpers.checkIfDateIsFuture
import com.softeng.ooyoo.helpers.dateDistance
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
import kotlinx.android.synthetic.main.activity_add_home_info.*




const val placeExtraName = "Athens"
const val dateExtraName ="30"

class AddHomeInfoActivity : AppCompatActivity() {
    private val house = House()
    private var dates = Dates()
    private var place = Place()
    private var addHomeReq = ""


    val uid = FirebaseAuth.getInstance().uid

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_home_info)

        place = intent.getParcelableExtra(placeExtraName)
        dates = intent.getParcelableExtra(dateExtraName)




        finishHostForm.setOnClickListener {
            if (uid == null){
                toast("There was an error while authenticating you.")
            }
            else if(place.name == "" || dates.startDate.isEmpty() || dates.endDate.isEmpty()){
                toast("Please add the place and the dates of your visit.")
            }
            else if (dateDistance(dates.startDate, dates.endDate) < 0){
                toast("The starting date needs to be before the ending date.")
            }
            else if (!checkIfDateIsFuture(dates.startDate)){
                toast("The date you selected has already passed. Please select a future date.")
            }

            else {

                val uid = FirebaseAuth.getInstance().uid

                val squareMeters = sqMeters.text.toString().toInt()
                val rooms = numRooms.text.toString().toInt()
                val numFlours = floor.text.toString().toInt()
                val homeSpec = edit_text_home_specifications.text.toString()

                house.size = squareMeters
                house.floor = numFlours
                house.numOfRooms = rooms


                val host = Hosting(
                    uid = uid,
                    place = place,
                    dates = dates,
                    house = house,
                    addHomeReq = homeSpec
                )

                val builder = AlertDialog.Builder(this)

                builder.setTitle("Are you sure you want to register this Host Form?")
                    .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->

                        val hostingDB = HostingDB()

                        hostingDB.hostRegistration(this, host) { id ->

                            finish()
                        }

                    }
                    .setNegativeButton("No") { _: DialogInterface, _: Int ->
                        finish()
                    }
                    .setNeutralButton("Alter") { _: DialogInterface, _: Int ->

                    }
                val dialog = builder.create()
                dialog.show()

            }
        }





        add_apartment_photos.setOnClickListener{
            toast("Under Construction")
        }



        /*
        finishHostForm.setOnClickListener {
            if (uid == null) {
                toast("There was an error while authenticating you.")
            } else if (place.name == "" || dates.startDate.isEmpty() || dates.endDate.isEmpty()) {
                toast("Please add the place and the dates of your visit.")
            } else if (dateDistance(dates.startDate, dates.endDate) < 0) {
                toast("The starting date needs to be before the ending date.")
            } else if (!checkIfDateIsFuture(dates.startDate)) {
                toast("The date you selected has already passed. Please select a future date.")
            } else {
                val intent = Intent(this, AddHomeInfo::class.java)
                startActivity(intent)
            }
        }

         */

    }
}