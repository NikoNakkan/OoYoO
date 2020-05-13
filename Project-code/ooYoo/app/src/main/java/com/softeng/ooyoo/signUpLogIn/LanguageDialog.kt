package com.softeng.ooyoo.signUpLogIn

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.softeng.ooyoo.helpers.LANGUAGE_ARRAY

class LanguageDialog: DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val selectedItems = arrayListOf<Int>()
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Language")
                .setMultiChoiceItems(LANGUAGE_ARRAY, null,
                    DialogInterface.OnMultiChoiceClickListener{ dialog: DialogInterface, which: Int, isChecked: Boolean ->
                        if (isChecked) {
                            selectedItems.add(which)
                        } else if (selectedItems.contains(which)) {
                            selectedItems.remove(Integer.valueOf(which))
                        }
                })
                .setPositiveButton("OK", DialogInterface.OnClickListener{ _: DialogInterface, _: Int ->
                    (activity as SignupAddInfoActivity).updateUserLanguages(selectedItems)
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { _: DialogInterface, _: Int ->

                })
            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }
}