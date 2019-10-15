package ru.task.cft.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_registration.*
import android.content.Intent
import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import ru.task.cft.R
import java.util.*


class RegistrationActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText

    private lateinit var dateBirthButton: Button
    private lateinit var passwordEditText: EditText
    private lateinit var passwordConfirmEditText: EditText
    private lateinit var submit: Button

    private var isFilledEditTexts: Boolean = false

    private val regex = Regex(".*\\d.*")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        nameEditText = name
        surnameEditText = surname

        dateBirthButton = date_birth

        passwordEditText = password
        passwordConfirmEditText = password_confirmation
        submit = registration
        submit.isEnabled = false

        dateBirthButton.setOnClickListener(this)
        submit.setOnClickListener(this)
        val edList = mutableListOf<EditText>().apply {
            add(nameEditText)
            add(surnameEditText)
            add(passwordEditText)
            add(passwordConfirmEditText)
        }

        val dateText = getString(R.string.date_birth_button_name)
        val textWatcher = object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                for (editText in edList) {
                    if (editText.text.toString().isEmpty()) {
                        isFilledEditTexts = false
//                        submit.isEnabled = false
                        break
                    } else {
                        isFilledEditTexts = true
                    }
                }

                if (!isFilledEditTexts || dateText == dateBirthButton.text) {
                    submit.isEnabled = false
                } else {
                    submit.isEnabled = true
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        }
        for (editText in edList) editText.addTextChangedListener(textWatcher)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.registration) {
            if (validateInputText()) {
                val intent = Intent(this, MainActivity::class.java)

                val extras = Bundle()
                extras.putString("EXTRA_NAME", name.text.toString())
                extras.putString("EXTRA_SURNAME", surname.text.toString())
                intent.putExtras(extras)

                startActivity(intent)
            }
        } else if (v?.id == R.id.date_birth) {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    dateBirthButton.setText("$day $month $year")

                    if (isFilledEditTexts) {
                        submit.isEnabled = true
                    }
                }, year, month, dayOfMonth
            )
            datePickerDialog.show()
        }
    }

    private fun validateInputText(): Boolean {
        val isName = validateName()
        val isSurname = validateSurname()
        val isPassword = validatePassword()
        val isConfirm = validateConfirmPassword()

        return isName && isSurname && isPassword && isConfirm
    }

    private fun validateConfirmPassword(): Boolean {
        if (passwordConfirmEditText.text.toString().isBlank()) {
            showDialog("Ошибка ввода данных", "Вы не подтвердили пароль")
            return false
        }

        if (passwordEditText.text.toString() != passwordConfirmEditText.text.toString()) {
            showDialog("Ошибка ввода данных", "Пароли не совпадают")
            return false
        }

        return true
    }

    private fun validatePassword(): Boolean {
        if (passwordEditText.text.toString().isBlank()) {
            showDialog("Ошибка ввода данных", "Пустой пароль")
            return false
        }

        if (passwordEditText.text.toString().length < 5) {
            showDialog("Ошибка ввода данных", "Длина пароля должна быть больше 5")
            return false
        }

        return true
    }

    private fun validateSurname(): Boolean {
        if (surnameEditText.text.toString().length < 2) {
            showDialog("Ошибка ввода данных", "Фамилия должна содержать более 1 буквы")
            return false
        }
        if (surnameEditText.text.toString().isBlank()) {
            showDialog("Ошибка ввода данных", "Пустая фамилия")
            return false
        }
        if (surnameEditText.text.toString().contains(regex)) {
            showDialog("Ошибка ввода данных", "В фамилии не должно быть цифр")
            return false
        }

        return true
    }

    private fun validateName(): Boolean {
        if (nameEditText.text.toString().isBlank()) {
            showDialog("Ошибка ввода данных", "Пустое имя")
            return false
        }

        if (nameEditText.text.toString().contains(regex)) {
            showDialog("Ошибка ввода данных", "В имени не должно быть цифр")
            return false
        }
        return true
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, which -> }
        builder.create().show()
    }
}
