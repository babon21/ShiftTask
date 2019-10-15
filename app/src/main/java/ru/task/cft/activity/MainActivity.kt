package ru.task.cft.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import ru.task.cft.GreetingDialog
import ru.task.cft.R

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var submit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submit = button
        submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.button) {
            val extras = intent.extras
            val name = extras?.getString("EXTRA_NAME") ?: "Ivan"
            val surname = extras?.getString("EXTRA_SURNAME") ?: "Ivanov"
            val dialog = GreetingDialog.newInstance(name, surname)
            dialog.show(supportFragmentManager, "dialog")
        }
    }
}
