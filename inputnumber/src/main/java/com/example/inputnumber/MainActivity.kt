package com.example.inputnumber

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editNumber: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var listView: ListView
    private lateinit var tvMessage: TextView
    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editNumber = findViewById(R.id.editNumber)
        radioGroup = findViewById(R.id.radioGroup)
        listView = findViewById(R.id.listView)
        tvMessage = findViewById(R.id.tvMessage)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter

        // Khi nhập số thay đổi
        editNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateList()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Khi đổi lựa chọn radio
        radioGroup.setOnCheckedChangeListener { _, _ -> updateList() }
    }

    private fun updateList() {
        val text = editNumber.text.toString()
        if (text.isEmpty()) {
            adapter.clear()
            tvMessage.visibility = TextView.VISIBLE
            tvMessage.text = "Vui lòng nhập số"
            return
        }

        val n = text.toInt()
        val selectedId = radioGroup.checkedRadioButtonId

        if (selectedId == -1) {
            tvMessage.visibility = TextView.VISIBLE
            tvMessage.text = "Chưa chọn loại số"
            adapter.clear()
            return
        }

        val list = (1 until n).filter { number ->
            when (selectedId) {
                R.id.rbOdd -> number % 2 != 0
                R.id.rbEven -> number % 2 == 0
                R.id.rbPrime -> isPrime(number)
                R.id.rbPerfect -> isPerfect(number)
                R.id.rbSquare -> isSquare(number)
                R.id.rbFibo -> isFibo(number)
                else -> false
            }
        }

        if (list.isEmpty()) {
            adapter.clear()
            tvMessage.text = "Không có số nào thỏa mãn"
            tvMessage.visibility = TextView.VISIBLE
        } else {
            tvMessage.visibility = TextView.GONE
            adapter.clear()
            adapter.addAll(list)
        }
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..Math.sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun isPerfect(n: Int): Boolean {
        if (n <= 1) return false
        var sum = 1
        for (i in 2..n / 2) if (n % i == 0) sum += i
        return sum == n
    }

    private fun isSquare(n: Int): Boolean {
        val root = Math.sqrt(n.toDouble()).toInt()
        return root * root == n
    }

    private fun isFibo(n: Int): Boolean {
        fun isPerfectSquare(x: Int) = isSquare(x)
        return isPerfectSquare(5 * n * n + 4) || isPerfectSquare(5 * n * n - 4)
    }
}
