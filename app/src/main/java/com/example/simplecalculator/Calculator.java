package com.example.simplecalculator;

public final class Calculator {
    private Calculator() {}

    public static double evaluate(String expression, boolean radians) {
        return new Parser(expression.replace('×', '*').replace('÷', '/').replace('−', '-'), radians).parse();
    }

    private static final class Parser {
        private final String input;
        private final boolean radians;
        private int position;

        private Parser(String input, boolean radians) {
            this.input = input;
            this.radians = radians;
        }

        private double parse() {
            double value = expression();
            skipSpaces();
            if (position != input.length()) throw error("Ký tự không hợp lệ");
            if (!Double.isFinite(value)) throw error("Kết quả không xác định");
            return value;
        }

        private double expression() {
            double value = term();
            while (true) {
                if (match('+')) value += term();
                else if (match('-')) value -= term();
                else return value;
            }
        }

        private double term() {
            double value = unary();
            while (true) {
                if (match('*')) value *= unary();
                else if (match('/')) {
                    double divisor = unary();
                    if (divisor == 0) throw error("Không thể chia cho 0");
                    value /= divisor;
                } else return value;
            }
        }

        private double unary() {
            if (match('+')) return unary();
            if (match('-')) return -unary();
            return power();
        }

        private double power() {
            double value = postfix();
            if (match('^')) return Math.pow(value, unary());
            if (match("ʸ√")) {
                if (value == 0) throw error("Bậc của căn phải khác 0");
                return Math.pow(unary(), 1.0 / value);
            }
            return value;
        }

        private double postfix() {
            double value = primary();
            while (true) {
                if (match('!')) value = factorial(value);
                else if (match('%')) value /= 100;
                else return value;
            }
        }

        private double primary() {
            if (match('(')) {
                double value = expression();
                if (!match(')')) throw error("Thiếu dấu )");
                return value;
            }

            skipSpaces();
            if (position >= input.length()) throw error("Thiếu giá trị");

            char current = input.charAt(position);
            if (Character.isDigit(current) || current == '.') return number();
            if (current == 'π') {
                position++;
                return Math.PI;
            }
            if (Character.isLetter(current)) {
                String name = identifier();
                if (name.equals("e")) return Math.E;
                if (!match('(')) throw error("Thiếu dấu ( sau " + name);
                double value = expression();
                if (!match(')')) throw error("Thiếu dấu )");
                return function(name, value);
            }
            throw error("Thiếu giá trị");
        }

        private double number() {
            int start = position;
            boolean decimal = false;
            while (position < input.length()) {
                char value = input.charAt(position);
                if (Character.isDigit(value)) position++;
                else if (value == '.' && !decimal) {
                    decimal = true;
                    position++;
                } else break;
            }
            if (position < input.length() && (input.charAt(position) == 'E' || input.charAt(position) == 'e')) {
                int exponent = position++;
                if (position < input.length() && (input.charAt(position) == '+' || input.charAt(position) == '-')) position++;
                int digits = position;
                while (position < input.length() && Character.isDigit(input.charAt(position))) position++;
                if (digits == position) position = exponent;
            }
            try {
                return Double.parseDouble(input.substring(start, position));
            } catch (NumberFormatException error) {
                throw error("Số không hợp lệ");
            }
        }

        private String identifier() {
            int start = position;
            while (position < input.length() && Character.isLetterOrDigit(input.charAt(position))) position++;
            return input.substring(start, position);
        }

        private double function(String name, double value) {
            double angle = radians ? value : Math.toRadians(value);
            switch (name) {
                case "sin": return Math.sin(angle);
                case "cos": return Math.cos(angle);
                case "tan": return Math.tan(angle);
                case "sinh": return Math.sinh(value);
                case "cosh": return Math.cosh(value);
                case "tanh": return Math.tanh(value);
                case "sqrt": return Math.sqrt(value);
                case "cbrt": return Math.cbrt(value);
                case "ln": return Math.log(value);
                case "log10": return Math.log10(value);
                case "exp": return Math.exp(value);
                default: throw error("Hàm không hợp lệ: " + name);
            }
        }

        private double factorial(double value) {
            if (value < 0 || value != Math.rint(value) || value > 170) {
                throw error("Giai thừa chỉ nhận số nguyên từ 0 đến 170");
            }
            double result = 1;
            for (int number = 2; number <= (int) value; number++) result *= number;
            return result;
        }

        private boolean match(char expected) {
            skipSpaces();
            if (position < input.length() && input.charAt(position) == expected) {
                position++;
                return true;
            }
            return false;
        }

        private boolean match(String expected) {
            skipSpaces();
            if (input.startsWith(expected, position)) {
                position += expected.length();
                return true;
            }
            return false;
        }

        private void skipSpaces() {
            while (position < input.length() && Character.isWhitespace(input.charAt(position))) position++;
        }

        private IllegalArgumentException error(String message) {
            return new IllegalArgumentException(message + " tại vị trí " + position);
        }
    }

    public static void main(String[] args) {
        check("2+3*4", 14);
        check("(5*9)+sin(90)", 46);
        check("2^3^2", 512);
        check("3ʸ√8", 2);
        check("5!", 120);
        check("sqrt(81)+log10(1000)", 12);
    }

    private static void check(String expression, double expected) {
        double actual = evaluate(expression, false);
        assert Math.abs(actual - expected) < 1e-9 : expression + " = " + actual;
    }
}
