package com.softeng.ooyoo.signUpLogIn

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.softeng.ooyoo.helpers.LANGUAGE_ARRAY

/**
 * This dialog is used to select the languages that the user speaks.
 */
class LanguageDialog: DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val selectedItems = arrayListOf<Int>()
            val builder = AlertDialog.Builder(it)

            builder.setTitle("Language")
                .setMultiChoiceItems(
                    LANGUAGE_ARRAY,
                    null
                ) { _: DialogInterface, which: Int, isChecked: Boolean ->
                    if (isChecked) {
                        selectedItems.add(which)
                    } else if (selectedItems.contains(which)) {
                        selectedItems.remove(Integer.valueOf(which))
                    }
                }
                .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                    (activity as SignupAddInfoActivity).updateUserLanguages(selectedItems)
                }
                .setNegativeButton("Cancel") { _: DialogInterface, _: Int ->

                }
            builder.create()


        } ?: throw IllegalStateException("Activity cannot be null")
    }
}