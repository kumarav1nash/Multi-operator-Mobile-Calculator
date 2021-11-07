package com.example.calculator.utility

import androidx.core.text.isDigitsOnly
import com.example.calculator.const.Constant
import java.math.RoundingMode
import java.text.DecimalFormat

private fun main(){
}

class Utility {

    /*
    public fun executeQuery(inputString: String,operator:String,negativeNumberSpotted:Boolean): String {
        //This functions responsibility is to get the string and perform all the null checks
        //check if we have got two number and a operator to perform calculations and
        //do the operation and format the result and return in form of String
        if (operator.isNullOrBlank() || operator.isEmpty()) return Constant.INVALID_INPUT
        var inpString = inputString
        if (negativeNumberSpotted) inpString =
            inpString.subSequence(1 until inpString.length).toString()
        val input = inpString.split(operator)

        var firstNumber = input[0]
        var secondNumber = input[1]

        if(firstNumber=="NaN" || firstNumber=="∞" || secondNumber=="NaN" || secondNumber=="∞") return Constant.INVALID_INPUT

        if (firstNumber.isNullOrEmpty() || firstNumber == "."||secondNumber=="." || secondNumber.isNullOrEmpty()) return Constant.INVALID_INPUT

        if (negativeNumberSpotted) {
            firstNumber = "-$firstNumber"
        }


        var result = 0.0
        when (operator) {
            "+" -> result = doAdd(firstNumber, secondNumber)
            "-" -> result = doSubtract(firstNumber, secondNumber)
            "*" -> result = doProduct(firstNumber, secondNumber)
            "/" -> result = doDivide(firstNumber, secondNumber)
        }


        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        firstNumber = df.format(result)
        return firstNumber

    }

    private fun doDivide(firstNumber: String, secondNumber: String): Double {
        return firstNumber.toDouble() / secondNumber.toDouble()
    }

    private fun doProduct(firstNumber: String, secondNumber: String): Double {
        return firstNumber.toDouble() * secondNumber.toDouble()
    }

    private fun doSubtract(firstNumber: String, secondNumber: String): Double {
        return firstNumber.toDouble() - secondNumber.toDouble()
    }

    private fun doAdd(firstNumber: String, secondNumber: String): Double {
        return firstNumber.toDouble() + secondNumber.toDouble()
    }*/



}