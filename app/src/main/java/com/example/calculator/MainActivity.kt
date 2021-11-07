package com.example.calculator

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.const.Constant
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

    private fun onClear() :Boolean{
        //this is the functions where everything will be reset
        binding.txtDisplayWindow.text = ""
        binding.txtRealTimeDisplayWindow.text = ""
        operator = ""
        hasDecimalValue = false
        negativeNumberSpotted = false
        return true

    }
    fun onBackSpace(view: View){
        var inputString:String = binding.txtDisplayWindow.text.toString()
        if (inputString.isNullOrEmpty()) return
        val lastChar = inputString[inputString.length-1]
        binding.txtDisplayWindow.text = inputString.substring(0 until inputString.length-1)
        binding.txtRealTimeDisplayWindow.text = ""
        if (lastChar=='.') hasDecimalValue = false
        if (lastChar=='-') negativeNumberSpotted = false
    }


    fun onDecimalKeyPressed(view: View) {
        if (!hasDecimalValue) {
            hasDecimalValue = true
            binding.txtDisplayWindow.append((view as Button).text)
        }
    }

    fun onKeyPressed(view:View){
        val pressedChar: String = (view as Button).text.toString()
        binding.txtDisplayWindow.append(pressedChar)
    }

    fun onOperatorKeyPressed(view: View) {
        val pressedChar: String = (view as Button).text.toString()
        val inpString = binding.txtDisplayWindow.text.toString()

        if ((inpString.isEmpty() || inpString == "0" || inpString == "-")) {
            if (pressedChar == "-") {
                binding.txtDisplayWindow.text = (pressedChar)

            }
            return
        }
        if (inpString.isEmpty()) return
        val lastChar = inpString.subSequence(inpString.length - 1 until inpString.length)

        if (lastChar == "+" || lastChar == "/" || lastChar == "*" || lastChar == "-") {
            operator = pressedChar
            hasDecimalValue = false
            binding.txtDisplayWindow.text =
                inpString.subSequence(0, inpString.length - 1).toString() + pressedChar
        } else if (inpString.contains("+") || inpString.contains("/") || inpString.contains("*") || inpString.contains(
                "-"
            )
        ) {
            //do nothing
            if (inpString.first() == '-') {
                negativeNumberSpotted = true
                if (!inpString.subSequence(1 until inpString.length).contains(Regex("[*-+/]"))) {
                    operator = pressedChar
                    hasDecimalValue = false
                    binding.txtDisplayWindow.append(pressedChar)
                }
            }
        } else {
            operator = pressedChar
            hasDecimalValue = false
            binding.txtDisplayWindow.append(pressedChar)
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onEqualKeyPressed(view: View) {
        Toast.makeText(this,binding.txtDisplayWindow.text.toString(),Toast.LENGTH_SHORT).show()
        val result = Calculator.evaluateII(binding.txtDisplayWindow.text.toString());


        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        val output  = df.format(result)

//        if (output == Constant.INVALID_INPUT) {
//            binding.txtRealTimeDisplayWindow.text = "Invalid format"
//            binding.txtRealTimeDisplayWindow.setTextColor(getColor(R.color.color_error))
//            return
//        } else {
//            binding.txtRealTimeDisplayWindow.text = ""
//        }
        binding.txtDisplayWindow.text = output.toString()
        operator = ""
        negativeNumberSpotted = output.first() == '-'
        hasDecimalValue = output.contains('.')


    }


}