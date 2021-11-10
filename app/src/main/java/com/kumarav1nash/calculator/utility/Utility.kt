package com.kumarav1nash.calculator.utility

import java.util.*

private fun main() {
}

class Utility {


    fun validateParenthesis(expr: String): Pair {

        //this program will return if the expr has valid parenthesis or not
        val par: Stack<Char> = Stack()
        for (ch: Char in expr.toCharArray()) {
            if (ch == '(') {
                par.push(ch)
            } else if (ch == ')') {
                par.pop()
            }
        }

        return Pair(par.size, par.isEmpty())
    }

    data class Pair(val count: Int, val isValid: Boolean)
}