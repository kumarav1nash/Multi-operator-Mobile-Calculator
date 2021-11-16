package com.kumarav1nash.calculator.utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Calculator {

    public static void main(String[] args) {
        System.out.println(cleanExpr("(64)-1*2.5"));
        System.out.println(evaluate("(184).7*8"));
        System.out.println(InfixToPostFix("(184).7*8"));
    }


    public static Double evaluate(String expr) {
        expr = cleanExpr(expr);
        Queue<String> tokenQueue = InfixToPostFix(expr);
        if (tokenQueue.isEmpty() || tokenQueue.size() < 3) return 0.0;
        System.out.println(tokenQueue);
        Stack<Double> resultStack = new Stack<>();
        while (!tokenQueue.isEmpty()) {
            String token = tokenQueue.poll();

            //System.out.println(token);
            assert token != null;
            if (token.length() == 1 && getPrecedence(token.charAt(0)) != -1) {
                //found a token
                Double secondOperand = resultStack.pop();
                Double firstOperand = resultStack.pop();
                Character sign = token.charAt(0);
                try {
                    resultStack.push(calculate(firstOperand, secondOperand, sign));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //not an operator, means a number
                resultStack.push(Double.parseDouble(token));

            }


        }

        //if done calculating there will be exactly one item in stack
        //System.out.println(resultStack.toString());
        return resultStack.peek();
    }


    private static String cleanExpr(String expr) {
        //this function will clean the expr
        //means it will make sure to place * wherever required inPlace of ( and )
        //something more that will come in future to make sure the output expr is valid for the algorithm
        expr = appendForMinus(expr);
        //System.out.println(expr);
        StringBuilder stringBuilder = new StringBuilder();
        boolean lastFoundTokenIsNotOpr = false;
        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            //for 8( or )( condition we need to place * in between
            if (ch == '(' && lastFoundTokenIsNotOpr) {
                stringBuilder.append('*');
                stringBuilder.append(ch);
            } else if (i > 0 && expr.charAt(i - 1) == ')' && (Character.isDigit(ch) || ch == '(' || ch == '.')) {
                //for )8 or )( condition we need to place * in between
                stringBuilder.append('*');
                stringBuilder.append(ch);
            } else {
                stringBuilder.append(ch);
            }


            lastFoundTokenIsNotOpr = Character.isDigit(ch) || ch == ')';


        }

        return stringBuilder.toString();

    }

    private static String appendForMinus(String expr) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.toCharArray()[i];
            if (ch == '-') {
                if (i == 0) {
                    stringBuilder.append("(0-1)");
                } else {
                    //if there is digit or ) before - then append +(0-1)
                    //else append (0-1)
                    if (Character.isDigit(expr.charAt(i - 1)) || expr.charAt(i - 1) == ')') {
                        stringBuilder.append("+(0-1)");
                    } else {
                        stringBuilder.append("(0-1)");

                    }
                }
            } else {
                stringBuilder.append(ch);
            }
        }
        return stringBuilder.toString();
    }

    private static Double calculate(Double firstOperand, Double secondOperand, Character sign) throws Exception {
        if (sign == '+') {
            return firstOperand + secondOperand;
        } else if (sign == '-') {
            return firstOperand - secondOperand;
        } else if (sign == '*') {
            return firstOperand * secondOperand;
        } else if (sign == '/') {
            return firstOperand / secondOperand;
        } else {
            throw new Exception("Unknown Operator found");
        }

    }

    //Implementing Dijkstra's Shunting Yard Algorithm

    /*********************************************************************************************/
    /*
    To build the algorithm, we will need
        1 stack for operations
        1 queue of the output
        1 array (or other list) of tokens.
    ============================================
    A pseudocode of the algorithm is as follows:
    ============================================
        1.  While there are tokens to be read:
        2.        Read a token
        3.        If it's a number add it to queue
        4.        If it's an operator
        5.               While there's an operator on the top of the stack with greater precedence:
        6.                       Pop operators from the stack onto the output queue
        7.               Push the current operator onto the stack
        8.        If it's a left bracket push it onto the stack
        9.        If it's a right bracket
        10.            While there's not a left bracket at the top of the stack:
        11.                     Pop operators from the stack onto the output queue.
        12.             Pop the left bracket from the stack and discard it
        13. While there are operators on the stack, pop them to the queue
    */

    /**********************************************************************************************/
    private static Queue<String> InfixToPostFix(String exp) {


        Stack<Character> operatorStack = new Stack<>();
        Queue<String> outputQueue = new LinkedList<>();
        StringBuilder numberToken = new StringBuilder();
        for (char ch : exp.toCharArray()) {

            //collect number if found
            if (Character.isDigit(ch) || ch == '.') {
                numberToken.append(ch);
                continue;
            }
            //if found a number add it to queue
            if (numberToken.length() > 0) {
                outputQueue.add(numberToken.toString());
                numberToken = new StringBuilder();
            }

            //if compiler is here this means ch does not contain number or decimal
            //hence it's an operator, ( or )
            if (ch != '(' && ch != ')') {
                if (!operatorStack.isEmpty()) {
                    //look for peek operator while its precedence is greater than curr char
                    //pop it and add it to queue
                    //and push the current operator to stack
                    while (!operatorStack.isEmpty() && operatorStack.peek() != '(' && getPrecedence(operatorStack.peek()) >= getPrecedence(ch)) {
                        outputQueue.add(operatorStack.pop() + "");
                    }

                }
                operatorStack.push(ch);

            } else if (ch == '(') {
                operatorStack.push(ch);
            } else {
                while (operatorStack.peek() != '(') {
                    outputQueue.add(operatorStack.pop() + "");
                }
                operatorStack.pop();
            }
        }
        //if found a number add it to queue
        if (numberToken.length() > 0) {
            outputQueue.add(numberToken.toString());
        }

        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop() + "");
        }


        //System.out.println(operatorStack.toString());
        return outputQueue;

    }

    private static int getPrecedence(char ch) {
        HashMap<Character, Integer> operatorPrecedenceMap = new HashMap<>();
        operatorPrecedenceMap.put('(', 16);

        operatorPrecedenceMap.put('*', 12);
        operatorPrecedenceMap.put('/', 12);
        operatorPrecedenceMap.put('%', 12);


        operatorPrecedenceMap.put('+', 11);
        operatorPrecedenceMap.put('-', 11);

        //if unknown operator Found return null else return it's precedence value
        return (operatorPrecedenceMap.get(ch) != null) ? operatorPrecedenceMap.get(ch) : -1;
    }


}
