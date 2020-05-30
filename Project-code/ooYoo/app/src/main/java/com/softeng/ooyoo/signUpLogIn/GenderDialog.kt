package com.softeng.ooyoo.signUpLogIn

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

/**
 * This dialog is used to select the user's gender.
 */
class GenderDialog: DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Gender")
                .setItems(arrayOf("Male", "Female", "Other"), DialogInterface.OnClickListener{ _: DialogInterface, which: Int ->
                    val gender = when(which){
                        0 -> "Male"
                        1 -> "Female"
                        2 -> "Other"
                        else -> "Other"
                    }
                    (activity as SignupAddInfoActivity).updateUserGender(gender)
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}