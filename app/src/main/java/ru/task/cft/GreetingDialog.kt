package ru.task.cft

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class GreetingDialog: DialogFragment() {
    companion object {

        fun newInstance(name: String, surname: String): GreetingDialog {
            val dialog = GreetingDialog()
            val args = Bundle().apply {
                putString("name", name)
                putString("surname", surname)
            }
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val name = arguments?.getString("name") ?: "Ivan"
        val surname = arguments?.getString("surname") ?: "Ivanov"
        builder.setTitle("Здравствуйте!")
            .setMessage("$surname $name")
            .setPositiveButton("OK") { dialog, which -> }

        return builder.create()
    }
}