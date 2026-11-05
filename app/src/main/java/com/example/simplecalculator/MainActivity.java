package com.example.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    private EditText firstNumber;
    private EditText secondNumber;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        result = findViewById(R.id.result);

        setOperation(R.id.addButton, '+');
        setOperation(R.id.subtractButton, '-');
        setOperation(R.id.multiplyButton, '*');
        setOperation(R.id.divideButton, '/');
    }

    private void setOperation(int buttonId, char operation) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(view -> calculate(operation));
    }

    private void calculate(char operation) {
        String firstText = firstNumber.getText().toString().trim();
        String secondText = secondNumber.getText().toString().trim();

        if (firstText.isEmpty() || secondText.isEmpty()) {
            Toast.makeText(this, R.string.enter_two_numbers, Toast.LENGTH_SHORT).show();
            return;
        }

        double a;
        double b;
        try {
            a = Double.parseDouble(firstText);
            b = Double.parseDouble(secondText);
        } catch (NumberFormatException error) {
            Toast.makeText(this, R.string.invalid_number, Toast.LENGTH_SHORT).show();
            return;
        }

        if (operation == '/' && b == 0) {
            result.setText(R.string.cannot_divide_by_zero);
            return;
        }

        double value = Calculator.calculate(a, b, operation);
        result.setText(getString(R.string.result_format, value));
    }
}
