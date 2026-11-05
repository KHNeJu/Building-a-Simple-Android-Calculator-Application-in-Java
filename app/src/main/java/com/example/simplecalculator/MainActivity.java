package com.example.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends Activity {
    private final DecimalFormat numberFormat = new DecimalFormat(
            "0.##########", DecimalFormatSymbols.getInstance(Locale.US));

    private TextView display;
    private double storedValue;
    private char pendingOperator;
    private boolean startNewNumber = true;
    private boolean hasError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        display = findViewById(R.id.display);

        int[] digitIds = {
                R.id.digit0, R.id.digit1, R.id.digit2, R.id.digit3, R.id.digit4,
                R.id.digit5, R.id.digit6, R.id.digit7, R.id.digit8, R.id.digit9
        };
        for (int id : digitIds) {
            findViewById(id).setOnClickListener(view ->
                    inputDigit(((Button) view).getText().charAt(0)));
        }

        findViewById(R.id.decimalButton).setOnClickListener(view -> inputDecimal());
        findViewById(R.id.clearButton).setOnClickListener(view -> clear());
        findViewById(R.id.signButton).setOnClickListener(view -> toggleSign());
        findViewById(R.id.percentButton).setOnClickListener(view -> percent());
        findViewById(R.id.addButton).setOnClickListener(view -> chooseOperator('+'));
        findViewById(R.id.subtractButton).setOnClickListener(view -> chooseOperator('-'));
        findViewById(R.id.multiplyButton).setOnClickListener(view -> chooseOperator('*'));
        findViewById(R.id.divideButton).setOnClickListener(view -> chooseOperator('/'));
        findViewById(R.id.equalsButton).setOnClickListener(view -> equalsResult());
    }

    private void inputDigit(char digit) {
        if (startNewNumber || hasError || display.getText().toString().equals("0")) {
            display.setText(String.valueOf(digit));
            startNewNumber = false;
            hasError = false;
            return;
        }
        display.append(String.valueOf(digit));
    }

    private void inputDecimal() {
        if (startNewNumber || hasError) {
            display.setText("0.");
            startNewNumber = false;
            hasError = false;
        } else if (display.getText().toString().indexOf('.') < 0) {
            display.append(".");
        }
    }

    private void clear() {
        display.setText("0");
        storedValue = 0;
        pendingOperator = 0;
        startNewNumber = true;
        hasError = false;
    }

    private void toggleSign() {
        if (hasError || display.getText().toString().equals("0")) return;
        String value = display.getText().toString();
        display.setText(value.startsWith("-") ? value.substring(1) : "-" + value);
        startNewNumber = false;
    }

    private void percent() {
        if (hasError) return;
        display.setText(format(readDisplay() / 100));
        startNewNumber = false;
    }

    private void chooseOperator(char operator) {
        if (hasError) return;
        if (pendingOperator != 0 && !startNewNumber && !calculate(readDisplay())) return;
        if (pendingOperator == 0) storedValue = readDisplay();

        pendingOperator = operator;
        startNewNumber = true;
    }

    private void equalsResult() {
        if (hasError || pendingOperator == 0 || startNewNumber) return;
        if (calculate(readDisplay())) {
            pendingOperator = 0;
            startNewNumber = true;
        }
    }

    private boolean calculate(double rightValue) {
        if (pendingOperator == '/' && rightValue == 0) {
            display.setText(R.string.error);
            pendingOperator = 0;
            startNewNumber = true;
            hasError = true;
            return false;
        }
        storedValue = Calculator.calculate(storedValue, rightValue, pendingOperator);
        display.setText(format(storedValue));
        return true;
    }

    private double readDisplay() {
        return Double.parseDouble(display.getText().toString());
    }

    private String format(double value) {
        return numberFormat.format(value);
    }
}
