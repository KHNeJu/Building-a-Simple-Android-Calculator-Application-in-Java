package com.example.simplecalculator;

public final class Calculator {
    private Calculator() {}

    public static double calculate(double a, double b, char operation) {
        switch (operation) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/': return a / b;
            default: throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }

    public static void main(String[] args) {
        assert calculate(2, 3, '+') == 5;
        assert calculate(5, 2, '-') == 3;
        assert calculate(4, 3, '*') == 12;
        assert calculate(8, 2, '/') == 4;
    }
}
