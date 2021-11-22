package com.kumarav1nash.calculator

import com.kumarav1nash.calculator.utility.Calculator
import junit.framework.TestCase.assertEquals
import org.junit.Test


class UnitTest {
    var exp1 = "2+233*(140-135)/3-(2-5+6)" //387.33

    var exp2 = "3-(5+(14-7*(14/2)-1)+45)" //-11

    var exp3 = "2+(3/4)+(23-(1+3*2))+1" //19.75

    var exp4 = "0-(3+(4+5))" //12.00

    var exp5 = "(1/2)*(25+4*2.5)*(4-2/10)" //66.5

    var exp6 = "(2.35-(2*5.2)*1.5*(6/3))" //-28.85

    var exp7 = "(2-(1.2*(4-1)-1)*3)" //-5.8

    var exp8 = "(2.35-(2*5.2)*1.5(6/3))" // -28.85

    var exp9 = "(2-(1.2(4-1)-1)3)" //-5.8

    var exp10 = "2-(1-4/5-1+2-1)" //1.8

    var exp11 = "45-(1/45)+1-4/2" // 43.977777

    var exp12 = "45+(0-1)(1/45)+1+(0-1)4/2" //43.977777

    var exp13 = "2+12(1*2-(1-6))" //86

    @Test
    fun evaluate_isCorrect(){
        val expAns1 = 387.3333333333333
        val expAns2 = -11.0
        val expAns3 = 19.75
        val expAns4 = -12.0
        val expAns5 = 66.5
        val expAns6 = -28.85
        val expAns7 = -5.799999999999999
        val expAns8 = 1.8
        val expAns9 = 43.977777777777774
        val expAns13 = 86.0
        assertEquals(expAns1, Calculator.evaluate(exp1))
        assertEquals(expAns2, Calculator.evaluate(exp2))
        assertEquals(expAns3, Calculator.evaluate(exp3))
        assertEquals(expAns4, Calculator.evaluate(exp4))
        assertEquals(expAns5, Calculator.evaluate(exp5))
        assertEquals(expAns6, Calculator.evaluate(exp6))
        assertEquals(expAns7, Calculator.evaluate(exp7))
        assertEquals(expAns6, Calculator.evaluate(exp8))
        assertEquals(expAns7, Calculator.evaluate(exp9))
        assertEquals(expAns8, Calculator.evaluate(exp10))
        assertEquals(expAns9, Calculator.evaluate(exp11))
        assertEquals(expAns9, Calculator.evaluate(exp12))
        assertEquals(expAns13, Calculator.evaluate(exp13))
    }
}