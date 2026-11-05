package com.example.simplecalculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
    private final DecimalFormat normalFormat = new DecimalFormat(
            "0.############", DecimalFormatSymbols.getInstance(Locale.US));
    private final DecimalFormat scientificFormat = new DecimalFormat(
            "0.##########E0", DecimalFormatSymbols.getInstance(Locale.US));
    private final ArrayList<String> history = new ArrayList<>();

    private TextView expressionDisplay;
    private TextView resultDisplay;
    private LinearLayout scientificPanel;
    private Button angleButton;
    private double memory;
    private boolean radians;
    private boolean justEvaluated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        expressionDisplay = findViewById(R.id.expressionDisplay);
        resultDisplay = findViewById(R.id.resultDisplay);
        scientificPanel = findViewById(R.id.scientificPanel);
        angleButton = findViewById(R.id.angleButton);

        int[] digits = {
                R.id.digit0, R.id.digit1, R.id.digit2, R.id.digit3, R.id.digit4,
                R.id.digit5, R.id.digit6, R.id.digit7, R.id.digit8, R.id.digit9
        };
        for (int id : digits) {
            findViewById(id).setOnClickListener(view -> appendDigit(((Button) view).getText().toString()));
        }

        setOperator(R.id.addButton, "+");
        setOperator(R.id.subtractButton, "−");
        setOperator(R.id.multiplyButton, "×");
        setOperator(R.id.divideButton, "÷");
        setOperator(R.id.powerButton, "^");
        setOperator(R.id.rootButton, "ʸ√");

        setValue(R.id.leftParenthesisButton, "(");
        setValue(R.id.piButton, "π");
        setValue(R.id.eulerButton, "e");
        setValue(R.id.reciprocalButton, "1/(");
        setValue(R.id.squareRootButton, "sqrt(");
        setValue(R.id.cubeRootButton, "cbrt(");
        setValue(R.id.expButton, "e^(");
        setValue(R.id.pow10Button, "10^(");
        setValue(R.id.lnButton, "ln(");
        setValue(R.id.logButton, "log10(");
        setValue(R.id.sinButton, "sin(");
        setValue(R.id.cosButton, "cos(");
        setValue(R.id.tanButton, "tan(");
        setValue(R.id.sinhButton, "sinh(");
        setValue(R.id.coshButton, "cosh(");
        setValue(R.id.tanhButton, "tanh(");

        setPostfix(R.id.squareButton, "^2");
        setPostfix(R.id.cubeButton, "^3");
        setPostfix(R.id.factorialButton, "!");
        setPostfix(R.id.percentButton, "%");

        findViewById(R.id.rightParenthesisButton).setOnClickListener(view -> appendRightParenthesis());
        findViewById(R.id.decimalButton).setOnClickListener(view -> appendDecimal());
        findViewById(R.id.eeButton).setOnClickListener(view -> appendExponent());
        findViewById(R.id.randomButton).setOnClickListener(view -> appendValue(format(Math.random())));
        findViewById(R.id.clearButton).setOnClickListener(view -> clear());
        findViewById(R.id.backspaceButton).setOnClickListener(view -> backspace());
        findViewById(R.id.signButton).setOnClickListener(view -> toggleSign());
        findViewById(R.id.equalsButton).setOnClickListener(view -> evaluate());
        findViewById(R.id.menuButton).setOnClickListener(view -> toggleScientificPanel());
        findViewById(R.id.historyButton).setOnClickListener(view -> showHistory());
        findViewById(R.id.secondButton).setOnClickListener(view -> {
            View row = findViewById(R.id.hyperbolicRow);
            row.setVisibility(row.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        });
        angleButton.setOnClickListener(view -> toggleAngleMode());

        findViewById(R.id.memoryClearButton).setOnClickListener(view -> memory = 0);
        findViewById(R.id.memoryAddButton).setOnClickListener(view -> memory += currentValue());
        findViewById(R.id.memorySubtractButton).setOnClickListener(view -> memory -= currentValue());
        findViewById(R.id.memoryRecallButton).setOnClickListener(view -> appendValue(format(memory)));
    }

    private void setOperator(int buttonId, String operator) {
        findViewById(buttonId).setOnClickListener(view -> appendOperator(operator));
    }

    private void setValue(int buttonId, String value) {
        findViewById(buttonId).setOnClickListener(view -> appendValue(value));
    }

    private void setPostfix(int buttonId, String postfix) {
        findViewById(buttonId).setOnClickListener(view -> appendPostfix(postfix));
    }

    private void appendDigit(String digit) {
        prepareForValue();
        String expression = expression();
        if (endsValue(expression)) expressionDisplay.append("×");
        expressionDisplay.append(digit);
    }

    private void appendValue(String value) {
        prepareForValue();
        String expression = expression();
        if (endsValue(expression) || (!expression.isEmpty()
                && (Character.isDigit(expression.charAt(expression.length() - 1))
                || expression.charAt(expression.length() - 1) == '.'))) {
            expressionDisplay.append("×");
        }
        expressionDisplay.append(value);
    }

    private void appendOperator(String operator) {
        if (justEvaluated) {
            expressionDisplay.setText(resultDisplay.getText());
            justEvaluated = false;
        }
        String expression = expression();
        if (expression.isEmpty()) {
            if (operator.equals("−")) expressionDisplay.setText(operator);
            return;
        }
        if (endsOperator(expression)) backspace();
        expressionDisplay.append(operator);
    }

    private void appendPostfix(String postfix) {
        if (justEvaluated) {
            expressionDisplay.setText(resultDisplay.getText());
            justEvaluated = false;
        }
        if (!expression().isEmpty() && !endsOperator(expression())) expressionDisplay.append(postfix);
    }

    private void appendRightParenthesis() {
        String expression = expression();
        if (count(expression, '(') > count(expression, ')') && !endsOperator(expression) && !expression.endsWith("(")) {
            expressionDisplay.append(")");
        }
    }

    private void appendDecimal() {
        prepareForValue();
        String expression = expression();
        if (endsValue(expression)) expressionDisplay.append("×0.");
        else if (expression.isEmpty() || endsOperator(expression) || expression.endsWith("(")) expressionDisplay.append("0.");
        else {
            int start = expression.length() - 1;
            while (start >= 0 && (Character.isDigit(expression.charAt(start)) || expression.charAt(start) == '.')) start--;
            if (!expression.substring(start + 1).contains(".")) expressionDisplay.append(".");
        }
    }

    private void appendExponent() {
        String expression = expression();
        if (!expression.isEmpty() && Character.isDigit(expression.charAt(expression.length() - 1))) {
            int start = expression.length() - 1;
            while (start >= 0 && (Character.isDigit(expression.charAt(start)) || expression.charAt(start) == '.')) start--;
            if (!expression.substring(start + 1).contains("E")) expressionDisplay.append("E");
        }
    }

    private void prepareForValue() {
        if (justEvaluated) {
            expressionDisplay.setText("");
            resultDisplay.setText("0");
            justEvaluated = false;
        }
    }

    private void clear() {
        expressionDisplay.setText("");
        resultDisplay.setText("0");
        justEvaluated = false;
    }

    private void backspace() {
        String expression = expression();
        if (expression.isEmpty()) return;
        int remove = expression.endsWith("ʸ√") ? 2 : 1;
        expressionDisplay.setText(expression.substring(0, Math.max(0, expression.length() - remove)));
    }

    private void toggleSign() {
        String expression = expression();
        if (expression.isEmpty()) expressionDisplay.setText("−");
        else if (expression.startsWith("−(") && expression.endsWith(")")) {
            expressionDisplay.setText(expression.substring(2, expression.length() - 1));
        } else expressionDisplay.setText("−(" + expression + ")");
    }

    private void evaluate() {
        String expression = closeParentheses(expression());
        if (expression.isEmpty() || endsOperator(expression)) return;
        try {
            double value = Calculator.evaluate(expression, radians);
            String formatted = format(value);
            expressionDisplay.setText(expression);
            resultDisplay.setText(formatted);
            history.add(0, expression + " = " + formatted);
            justEvaluated = true;
        } catch (IllegalArgumentException error) {
            resultDisplay.setText(R.string.error);
        }
    }

    private double currentValue() {
        try {
            if (!expression().isEmpty()) return Calculator.evaluate(closeParentheses(expression()), radians);
            return Double.parseDouble(resultDisplay.getText().toString());
        } catch (RuntimeException error) {
            resultDisplay.setText(R.string.error);
            return 0;
        }
    }

    private void toggleScientificPanel() {
        scientificPanel.setVisibility(scientificPanel.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void toggleAngleMode() {
        radians = !radians;
        angleButton.setText(radians ? "Deg" : "Rad");
    }

    private void showHistory() {
        String[] items = history.isEmpty()
                ? new String[]{getString(R.string.history_empty)}
                : history.toArray(new String[0]);
        new AlertDialog.Builder(this)
                .setTitle(R.string.history_title)
                .setItems(items, null)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private String closeParentheses(String expression) {
        StringBuilder result = new StringBuilder(expression);
        for (int missing = count(expression, '(') - count(expression, ')'); missing > 0; missing--) result.append(')');
        return result.toString();
    }

    private int count(String value, char target) {
        int total = 0;
        for (int index = 0; index < value.length(); index++) if (value.charAt(index) == target) total++;
        return total;
    }

    private boolean endsValue(String expression) {
        if (expression.isEmpty()) return false;
        char last = expression.charAt(expression.length() - 1);
        return last == ')' || last == 'π' || last == 'e' || last == '!' || last == '%';
    }

    private boolean endsOperator(String expression) {
        return expression.endsWith("+") || expression.endsWith("−") || expression.endsWith("×")
                || expression.endsWith("÷") || expression.endsWith("^") || expression.endsWith("ʸ√");
    }

    private String expression() {
        return expressionDisplay.getText().toString();
    }

    private String format(double value) {
        double absolute = Math.abs(value);
        return absolute != 0 && (absolute >= 1_000_000_000_000d || absolute < 0.000000001d)
                ? scientificFormat.format(value)
                : normalFormat.format(value);
    }
}
