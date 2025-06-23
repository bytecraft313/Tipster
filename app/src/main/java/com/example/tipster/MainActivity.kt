package com.example.tipster

import android.os.Bundle
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var billAmountEditText: EditText
    private lateinit var tipLabel: TextView
    private lateinit var tipAmountTextView: TextView
    private lateinit var totalAmountTextView: TextView
    private lateinit var tipSeekBar: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        billAmountEditText = findViewById(R.id.etBillAmount)
        tipLabel = findViewById(R.id.tvTipLabel)
        tipAmountTextView = findViewById(R.id.tvTipAmount)
        totalAmountTextView = findViewById(R.id.tvTotalAmount)
        tipSeekBar = findViewById(R.id.seekBarTip)

        tipSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tipLabel.text = "Tip Percentage: $progress%"
                calculateTip()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { /* no-op */ }

            override fun onStopTrackingTouch(seekBar: SeekBar?) { /* no-op */ }
        })

        // Optional: Calculate tip when user finishes typing bill amount
        billAmountEditText.setOnEditorActionListener { _, _, _ ->
            calculateTip()
            true
        }
    }

    private fun calculateTip() {
        val input = billAmountEditText.text.toString()
        if (input.isEmpty()) {
            tipAmountTextView.text = "Tip: $0.00"
            totalAmountTextView.text = "Total: $0.00"
            return
        }

        val bill = input.toDoubleOrNull() ?: 0.0
        val tipPercent = tipSeekBar.progress
        val tip = bill * tipPercent / 100
        val total = bill + tip

        val currencyFormat = NumberFormat.getCurrencyInstance()
        tipAmountTextView.text = "Tip: ${currencyFormat.format(tip)}"
        totalAmountTextView.text = "Total: ${currencyFormat.format(total)}"
    }
}
