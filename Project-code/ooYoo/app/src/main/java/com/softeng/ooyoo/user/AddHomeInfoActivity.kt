
package com.softeng.ooyoo.user

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.softeng.ooyoo.R
import com.softeng.ooyoo.databases.TripPlansDB
import com.softeng.ooyoo.databases.UserDB
import com.softeng.ooyoo.helpers.checkIfDateIsFuture
import com.softeng.ooyoo.helpers.dateDistance
import com.softeng.ooyoo.helpers.toast
import com.softeng.ooyoo.place.Place
import com.softeng.ooyoo.travel.Dates
import com.softeng.ooyoo.trip.TripPlan
import kotlinx.android.synthetic.main.activity_add_home_info.*
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.storage.*
import com.google.firebase.storage.StorageException

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream

class AddHomeInfoActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_home_info)

        //includesForCreateReference()



        submitText.setOnClickListener {
            var input = editText.text




            finishHostForm.setOnClickListener {
                val trip = TripPlan(uid = uid, place = place, dates = dates)
                val uid = FirebaseAuth.getInstance().uid
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Are you sure you want to register this trip?")
                    .setPositiveButton("Yes!") { _: DialogInterface, _: Int ->

                        val tripPlansDB = TripPlansDB()

                        tripPlansDB.tripRegistration(this, trip) { id ->

                            finish()
                        }

                    }
                    .setNegativeButton("No") { _: DialogInterface, _: Int ->
                        finish()
                    }
                    .setNeutralButton("Alter") { _: DialogInterface, _: Int ->
                        // make changes in prev dialog
                        onBackPressed()
                    }
                val dialog = builder.create()
                dialog.show()
            }

        }




    }





    // [START storage_field_declaration]
    lateinit var storage: FirebaseStorage
    // [END storage_field_declaration]





    private fun includesForCreateReference() {
        // val storage = Firebase.storage

        // ## Create a Reference

        // [START create_storage_reference]
        // Create a storage reference from our app
        var storageRef = storage.reference
        // [END create_storage_reference]

        // [START create_child_reference]
        // Create a child reference
        // imagesRef now points to "images"
        var imagesRef: StorageReference? = storageRef.child("images")

        // Child references can also take paths
        // spaceRef now points to "images/space.jpg
        // imagesRef still points to "images"
        var spaceRef = storageRef.child("images/space.jpg")
        // [END create_child_reference]

        // ## Navigate with References

        // [START navigate_references]
        // parent allows us to move our reference to a parent node
        // imagesRef now points to 'images'
        imagesRef = spaceRef.parent

        // root allows us to move all the way back to the top of our bucket
        // rootRef now points to the root
        val rootRef = spaceRef.root
        // [END navigate_references]

        // [START chain_navigation]
        // References can be chained together multiple times
        // earthRef points to 'images/earth.jpg'
        val earthRef = spaceRef.parent?.child("earth.jpg")

        // nullRef is null, since the parent of root is null
        val nullRef = spaceRef.root.parent
        // [END chain_navigation]

        // ## Reference Properties

        // [START reference_properties]
        // Reference's path is: "images/space.jpg"
        // This is analogous to a file path on disk
        spaceRef.path

        // Reference's name is the last segment of the full path: "space.jpg"
        // This is analogous to the file name
        spaceRef.name

        // Reference's bucket is the name of the storage bucket that the files are stored in
        spaceRef.bucket
        // [END reference_properties]

        // ## Full Example

        // [START reference_full_example]
        // Points to the root reference
        storageRef = storage.reference

        // Points to "images"
        imagesRef = storageRef.child("images")

        // Points to "images/space.jpg"
        // Note that you can use variables to create child values
        val fileName = "space.jpg"
        spaceRef = imagesRef.child(fileName)

        // File path is "images/space.jpg"
        val path = spaceRef.path

        // File name is "space.jpg"
        val name = spaceRef.name

        // Points to "images"
        imagesRef = spaceRef.parent
        // [END reference_full_example]
    }







    private val endTravelDate = mutableMapOf<String, Int>()
    private val dates = Dates()
    private val place = Place()
    val uid = FirebaseAuth.getInstance().uid






    // [START storage_custom_failure_listener]
    internal inner class MyFailureListener : OnFailureListener {
        override fun onFailure(exception: Exception) {
            val errorCode = (exception as StorageException).errorCode
            val errorMessage = exception.message
            // test the errorCode and errorMessage, and handle accordingly
        }
    }
    // [END storage_custom_failure_listener]





}

