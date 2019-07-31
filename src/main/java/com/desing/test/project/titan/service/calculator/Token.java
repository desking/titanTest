package com.desing.test.project.titan.service.calculator;

public class Token {
    private TokenType type;
    private double value;
    private char operator;
    private int precedence;

    public Token() {
        type = TokenType.UNKNOWN;
    }

    public Token(String contents) {
        switch (contents) {
            case "+":
                type = TokenType.OPERATOR;
                operator = contents.charAt(0);
                precedence = 1;
                break;
            case "-":
                type = TokenType.OPERATOR;
                operator = contents.charAt(0);
                precedence = 1;
                break;
            case "*":
                type = TokenType.OPERATOR;
                operator = contents.charAt(0);
                precedence = 2;
                break;
            case "/":
                type = TokenType.OPERATOR;
                operator = contents.charAt(0);
                precedence = 2;
                break;
            case "(":
                type = TokenType.LEFT_PARENTHESES;
                break;
            case ")":
                type = TokenType.RIGHT_PARENTHESES;
                break;
            default:
                type = TokenType.NUMBER;
                try {
                    value = Double.parseDouble(contents);
                } catch (Exception ex) {
                    type = TokenType.UNKNOWN;
                }
        }
    }

    public Token(double x) {
        type = TokenType.NUMBER;
        value = x;
    }

    TokenType getType() {
        return type;
    }

    double getValue() {
        return value;
    }

    int getPrecedence() {
        return precedence;
    }

    public char getOperator() {
        return operator;
    }
}