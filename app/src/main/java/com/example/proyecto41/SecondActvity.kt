package com.example.proyecto41

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto41.databinding.ActivitySecondActvityBinding
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import android.content.Intent


class SecondActvity : AppCompatActivity() {

    //1. Declaramos la variable binding
    private lateinit var binding: ActivitySecondActvityBinding
    private lateinit var amountEditText: TextInputEditText
    private lateinit var currencySpinner: AutoCompleteTextView
    private lateinit var resultTextView: MaterialTextView

    private val exchangeRates = mapOf(
        "USD" to 0.27, // 1 PEN = 0.27 USD
        "EUR" to 0.25, // 1 PEN = 0.25 EUR
        "MXN" to 4.62  // 1 PEN = 4.62 MXN
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_actvity)

        //2. Inicializamos el binding
        binding = ActivitySecondActvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //3. Obtenemos el valor del username
        val username = intent.getStringExtra("username")
        
        amountEditText = findViewById(R.id.amountEditText)
        currencySpinner = findViewById(R.id.currencySpinner)
        resultTextView = findViewById(R.id.resultTextView)

        setupCurrencySpinner()
        setupAmountEditText()

        //Configuramos el botón para cerrar sesión
        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }

    private fun setupCurrencySpinner() {
        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, R.layout.dropdown_item, currencies)
        currencySpinner.setAdapter(adapter)
        currencySpinner.setText(currencies[0], false)
        currencySpinner.setOnItemClickListener { _, _, _, _ ->
            updateResult()
        }
    }

    private fun setupAmountEditText() {
        amountEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateResult()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun updateResult() {
        val amount = amountEditText.text.toString().toDoubleOrNull() ?: 0.0
        val selectedCurrency = currencySpinner.text.toString()
        val rate = exchangeRates[selectedCurrency] ?: 0.0
        val convertedAmount = amount * rate
        resultTextView.text = String.format("%.2f %s", convertedAmount, selectedCurrency)

        val color = when {
            convertedAmount > 1000 -> R.color.high_amount_color
            convertedAmount > 500 -> R.color.medium_amount_color
            else -> R.color.low_amount_color
        }
        resultTextView.setTextColor(ContextCompat.getColor(this, color))

        // Animate the result text
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        resultTextView.startAnimation(animation)
    }
}