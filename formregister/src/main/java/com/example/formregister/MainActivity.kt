package com.example.formregister

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var etBirthday: EditText
    private lateinit var btnSelectBirthday: Button
    private lateinit var calendarView: CalendarView
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var cbTerms: CheckBox
    private lateinit var btnRegister: Button

    private var isCalendarVisible = false

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ánh xạ view
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        rgGender = findViewById(R.id.rgGender)
        etBirthday = findViewById(R.id.etBirthday)
        btnSelectBirthday = findViewById(R.id.btnSelectBirthday)
        etAddress = findViewById(R.id.etAddress)
        etEmail = findViewById(R.id.etEmail)
        cbTerms = findViewById(R.id.cbTerms)
        btnRegister = findViewById(R.id.btnRegister)

        // Thêm CalendarView ẩn bên dưới EditText
        calendarView = CalendarView(this)
        val parentLayout = findViewById<LinearLayout>(R.id.mainLayout)
        parentLayout.addView(calendarView)
        calendarView.visibility = View.GONE

        // Nhấn Select để hiện / ẩn Calendar
        btnSelectBirthday.setOnClickListener {
            isCalendarVisible = !isCalendarVisible
            calendarView.visibility = if (isCalendarVisible) View.VISIBLE else View.GONE
        }

        // Chọn ngày từ Calendar → cập nhật EditText
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
            etBirthday.setText(selectedDate)
            calendarView.visibility = View.GONE
            isCalendarVisible = false
        }

        // Kiểm tra khi nhấn Register
        btnRegister.setOnClickListener { validateInputs() }
    }

    private fun validateInputs() {
        var isValid = true

        fun checkEmpty(editText: EditText): Boolean {
            return if (editText.text.toString().trim().isEmpty()) {
                editText.setBackgroundColor(Color.parseColor("#FFCDD2")) // đỏ nhạt
                false
            } else {
                editText.setBackgroundColor(Color.WHITE)
                true
            }
        }

        if (!checkEmpty(etFirstName)) isValid = false
        if (!checkEmpty(etLastName)) isValid = false
        if (!checkEmpty(etBirthday)) isValid = false
        if (!checkEmpty(etAddress)) isValid = false
        if (!checkEmpty(etEmail)) isValid = false

        // Giới tính
        if (rgGender.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        // Terms
        if (!cbTerms.isChecked) {
            Toast.makeText(this, "Please agree to Terms of Use", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (isValid) {
            Toast.makeText(this, "Register successful!", Toast.LENGTH_LONG).show()
        }
    }
}
