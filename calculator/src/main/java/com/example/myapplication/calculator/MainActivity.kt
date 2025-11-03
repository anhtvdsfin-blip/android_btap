package com.example.myapplication.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvExpression: TextView

    private var currentInput = ""
    private var operator = ""
    private var firstOperand = 0
    private var isNewInput = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvExpression = findViewById(R.id.tvExpression)

        val grid = findViewById<GridLayout>(R.id.gridButtons)
        for (i in 0 until grid.childCount) {
            val button = grid.getChildAt(i) as Button
            button.setOnClickListener { onButtonClick(button) }
        }
    }

    private fun onButtonClick(button: Button) {
        val value = button.text.toString()

        when (value) {
            in "0".."9" -> {
                if (isNewInput) {
                    currentInput = ""
                    isNewInput = false
                }
                currentInput += value
                tvResult.text = currentInput
            }

            "+", "-", "×", "÷" -> setOperator(value)
            "=" -> calculateResult()
            "C" -> clearAll()
            "CE" -> clearEntry()
            "BS" -> backspace()
            "+/-" -> toggleSign()
        }
    }

    private fun setOperator(op: String) {
        firstOperand = currentInput.toIntOrNull() ?: 0
        operator = op
        isNewInput = true
        tvExpression.text = "$firstOperand $operator"  // ✅ Hiển thị phép tính
    }

    private fun calculateResult() {
        val secondOperand = currentInput.toIntOrNull() ?: 0
        val result = when (operator) {
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "×" -> firstOperand * secondOperand
            "÷" -> {
                if (secondOperand == 0) {
                    tvResult.text = "Error"
                    tvExpression.text = "$firstOperand $operator $secondOperand"
                    return
                } else firstOperand / secondOperand
            }
            else -> secondOperand
        }

        tvExpression.text = "$firstOperand $operator $secondOperand =" // ✅ Hiển thị phép tính hoàn chỉnh
        tvResult.text = result.toString()
        currentInput = result.toString()
        isNewInput = true
    }

    private fun clearAll() {
        currentInput = ""
        operator = ""
        firstOperand = 0
        tvResult.text = "0"
        tvExpression.text = ""
        isNewInput = true
    }

    private fun clearEntry() {
        currentInput = ""
        tvResult.text = "0"
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            tvResult.text = currentInput.ifEmpty { "0" }
        }
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty()) {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.drop(1)
            } else {
                "-$currentInput"
            }
            tvResult.text = currentInput
        }
    }
}
