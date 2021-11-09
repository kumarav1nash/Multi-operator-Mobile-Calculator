package com.example.calculator

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.example.calculator.databinding.ActivityMainBinding
import com.example.calculator.utility.Calculator
import com.example.calculator.utility.Utility
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private var negativeNumberSpotted: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private var operator: String = ""
    private var hasDecimalValue = false
    private var equalPressedCounter =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnBackSpace.setOnLongClickListener {
            onClear()
        }
    }

    fun onNumKeyPressed(view: View) {
        val pressedChar: String = (view as Button).text.toString()
        binding.txtDisplayWindow.append(pressedChar)
    }

    private fun onClear(): Boolean {
        //this is the functions where everything will be reset
        binding.txtDisplayWindow.text = ""
        binding.txtRealTimeDisplayWindow.text = ""
        operator = ""
        hasDecimalValue = false
        negativeNumberSpotted = false
        equalPressedCounter = 0
        return true

    }

    fun onBackSpace(view: View) {
        var inputString: String = binding.txtDisplayWindow.text.toString()
        if (inputString.isNullOrEmpty()) return
        val lastChar = inputString[inputString.length - 1]
        binding.txtDisplayWindow.text = inputString.substring(0 until inputString.length - 1)
        binding.txtRealTimeDisplayWindow.text = ""
        equalPressedCounter =0
        if (lastChar == '.') hasDecimalValue = false
        if (lastChar == '-') negativeNumberSpotted = false
    }


    fun onDecimalKeyPressed(view: View) {
        if (!hasDecimalValue) {
            hasDecimalValue = true
            binding.txtDisplayWindow.append((view as Button).text)
        }
    }

    fun onKeyPressed(view: View) {
        val pressedChar: String = (view as Button).text.toString()

        //check whether decimal should active or not
        if (!Character.isDigit(pressedChar[0])) {
            hasDecimalValue = false;
        }
        //check if the string have valid parenthesis if so we will not append
        if (pressedChar == ")" && Utility().validateParenthesis(binding.txtDisplayWindow.text.toString()).isValid) return

        var inputString = binding.txtDisplayWindow.text.toString()

        //check if input string is empty and a operator is pressed
        if (inputString.isEmpty()){
            if (!pressedChar.isDigitsOnly() && (pressedChar!="(" && pressedChar!=")")){

                if (pressedChar!="-"){
                    return
                }
            }
        }

        //this will check if last char is operator ie not digit not (,) and curr is also operator
        //then we have two condition
        //if curr is - then check if last one is - if so do not append
        //if curr char

        if (inputString.isNotEmpty() && !pressedChar.isDigitsOnly() && (pressedChar!="(" && pressedChar!=")")){
            val lastChar: Char = inputString[inputString.length-1]
            if(pressedChar=="-"){
                //check if last is also minus if so return
                if (lastChar=='-'){
                    return
                }
            }else{
                if (!lastChar.isDigit()){
                    return
                }
            }
        }
        equalPressedCounter =0
        binding.txtDisplayWindow.append(pressedChar)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onEqualKeyPressed(view: View) {
        //Toast.makeText(this, binding.txtDisplayWindow.text.toString(), Toast.LENGTH_SHORT).show()
        var inputString = binding.txtDisplayWindow.text.toString()

        //first check if expr has valid parenthesis if not not make it valid
        if (!Utility().validateParenthesis(inputString).isValid){
            val count = Utility().validateParenthesis(inputString).count
            inputString+="1";
            for (i in 1..count){
                inputString+=")"
            }

        }
        val result = Calculator.evaluate(inputString);


        val df = DecimalFormat("#.##########")
        df.roundingMode = RoundingMode.CEILING
        val output = df.format(result)

        binding.txtRealTimeDisplayWindow.text = output.toString()
        operator = ""
        negativeNumberSpotted = output.first() == '-'
        hasDecimalValue = output.contains('.')


    }


}