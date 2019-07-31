package com.desing.test.project.titan.service.calculator;

import com.desing.test.project.titan.exception.ExpressionsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.desing.test.project.titan.service.calculator.TokenType.LEFT_PARENTHESES;
import static com.desing.test.project.titan.service.calculator.TokenType.OPERATOR;

public class Calculator {
    private static final Predicate<Stack<Token>> PREDICATE_BY_OPERATOR = tokenStack -> !tokenStack.isEmpty() &&
            tokenStack.peek().getType().equals(OPERATOR);

    private Stack<Token> operatorStack;
    private Stack<Token> valueStack;

    public Calculator() {
        operatorStack = new Stack<>();
        valueStack = new Stack<>();
    }

    private Token calculate(double a, double b, char operator) {
        switch (operator) {
            case '+':
                return new Token(a + b);
            case '-':
                return new Token(a - b);
            case '*':
                return new Token(a * b);
            case '/':
                return new Token(a / b);
            default:
                return new Token(0);
        }
    }

    private void processOperator(Token token) {
        Token a, b;
        try {
            b = valueStack.pop();
            a = valueStack.pop();
        } catch (Exception e) {
            throw new ExpressionsException("ExpressionResult error.");
        }
        Token result = calculate(a.getValue(), b.getValue(), token.getOperator());
        valueStack.push(result);
    }


    public double calculate(String expression) throws ExpressionsException{
        List<Token> tokens = getExpressionParts(expression).stream()
                .map(Token::new)
                .collect(Collectors.toList());

        for (Token nextToken : tokens) {
            switch (nextToken.getType()) {
                case NUMBER: {
                    valueStack.push(nextToken);
                    break;
                }
                case OPERATOR: {
                    processWithOperator(nextToken);
                    break;
                }
                case LEFT_PARENTHESES: {
                    operatorStack.push(nextToken);
                    break;
                }
                case RIGHT_PARENTHESES: {
                    processWithBraces();
                    break;
                }
            }
        }
        processWithOperatorStackByPredicate(PREDICATE_BY_OPERATOR);

        Token result = valueStack.peek();
        valueStack.pop();
        if (!operatorStack.isEmpty() || !valueStack.isEmpty()) {
            throw new ExpressionsException("ExpressionResult error.");
        } else {
            return result.getValue();
        }
    }

    private void processWithOperatorStackByPredicate(Predicate<Stack<Token>> predicate) {
        while (predicate.test(operatorStack)) {
            Token toProcess = operatorStack.peek();
            operatorStack.pop();
            processOperator(toProcess);
        }
    }

    private void processWithOperator(Token token) {
        if (operatorStack.isEmpty() || token.getPrecedence() > operatorStack.peek().getPrecedence()) {
            operatorStack.push(token);
        } else {
            Predicate<Stack<Token>> predicate = tokenStack -> !tokenStack.isEmpty() &&
                    token.getPrecedence() <= tokenStack.peek().getPrecedence();
            processWithOperatorStackByPredicate(predicate);
            operatorStack.push(token);
        }
    }

    private void processWithBraces() {
        processWithOperatorStackByPredicate(PREDICATE_BY_OPERATOR);
        if (!operatorStack.isEmpty() && operatorStack.peek().getType().equals(LEFT_PARENTHESES)) {
            operatorStack.pop();
        } else {
            throw new ExpressionsException("Error: unbalanced parenthesis.");
        }
    }

    private List<String> getExpressionParts(String expression) {
        expression = expression.replaceAll(" ", "");
        String numbers = "0123456789";
        List<String> expressionParts = new ArrayList<>();
        int expressionLength = expression.length();
        int lastAddedIndex = 0;
        for (int i = 1; i <= expressionLength; i++) {
            if (!numbers.contains(expression.substring(i - 1, i))) {
                String part = expression.substring(lastAddedIndex, i - 1);
                if (!part.equals("")) {
                    expressionParts.add(part);
                }
                expressionParts.add(expression.substring(i - 1, i));
                lastAddedIndex = i;
            } else if (i == expressionLength) {
                expressionParts.add(expression.substring(lastAddedIndex, expressionLength));
            }
        }
        return expressionParts;
    }
}
