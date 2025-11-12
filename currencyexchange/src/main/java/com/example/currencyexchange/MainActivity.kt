package com.example.currencyexchange

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var editFrom: EditText
    private lateinit var editTo: EditText

    private var isEditingFrom = false
    private var isEditingTo = false

    // Bảng tỷ giá cố định so với USD
    private val rates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.92,
        "VND" to 25000.0,
        "JPY" to 150.0,
        "GBP" to 0.78,
        "AUD" to 1.45,
        "CAD" to 1.37,
        "CNY" to 7.1,
        "KRW" to 1380.0,
        "SGD" to 1.34
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        editFrom = findViewById(R.id.editFrom)
        editTo = findViewById(R.id.editTo)

        val currencies = rates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, currencies)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerFrom.setSelection(0)
        spinnerTo.setSelection(1)

        // Khi thay đổi nội dung EditText (ô nhập tiền)
        editFrom.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isEditingTo) return
                isEditingFrom = true
                convert(editFrom, editTo, spinnerFrom, spinnerTo)
                isEditingFrom = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Khi thay đổi EditText kết quả → tính ngược lại
        editTo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (isEditingFrom) return
                isEditingTo = true
                convert(editTo, editFrom, spinnerTo, spinnerFrom)
                isEditingTo = false
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Khi đổi loại tiền
        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convert(editFrom, editTo, spinnerFrom, spinnerTo)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerFrom.onItemSelectedListener = listener
        spinnerTo.onItemSelectedListener = listener
    }

    private fun convert(fromEdit: EditText, toEdit: EditText, fromSpinner: Spinner, toSpinner: Spinner) {
        val fromCurrency = fromSpinner.selectedItem.toString()
        val toCurrency = toSpinner.selectedItem.toString()

        val amount = fromEdit.text.toString().toDoubleOrNull()
        if (amount == null || amount < 0) {
            toEdit.setText("")
            return
        }

        val usdAmount = amount / (rates[fromCurrency] ?: 1.0)
        val converted = usdAmount * (rates[toCurrency] ?: 1.0)

        toEdit.setText(String.format("%.2f", converted))
    }
}
