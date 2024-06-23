package com.demo.datechecker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText dayInput;
    private EditText monthInput;
    private EditText yearInput;
    private TextView resultTextView;
    private Button checkDateButton;
    private Button clearButton;
    private Button closeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayInput = findViewById(R.id.dayInput);
        monthInput = findViewById(R.id.monthInput);
        yearInput = findViewById(R.id.yearInput);
        resultTextView = findViewById(R.id.resultTextView);
        checkDateButton = findViewById(R.id.checkDateButton);
        clearButton = findViewById(R.id.clearButton);
        closeButton = findViewById(R.id.closeButton);

        checkDateButton.setOnClickListener(v -> checkDate());
        clearButton.setOnClickListener(v -> clearFields());
        closeButton.setOnClickListener(v -> finish()); // Close the app
    }

    public void checkDate() {
        try {
            int day = Integer.parseInt(dayInput.getText().toString());
            int month = Integer.parseInt(monthInput.getText().toString());
            int year = Integer.parseInt(yearInput.getText().toString());

            if (!isValidDay(day)) {
                Toast.makeText(MainActivity.this, "Day must be an integer between 1 and 31.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidMonth(month)) {
                Toast.makeText(MainActivity.this, "Month must be an integer between 1 and 12.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidYear(year)) {
                Toast.makeText(MainActivity.this, "Year must be an integer between 1000 and 3000.", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isValid = isValidDate(day, month, year);
            if (isValid) {
                String dateMessage = String.format("%02d/%02d/%04d is a valid date.", day, month, year);
                Toast.makeText(MainActivity.this, dateMessage, Toast.LENGTH_SHORT).show();
                resultTextView.setText(dateMessage);
            } else {
                String dateMessage = String.format("%02d/%02d/%04d is not a valid date.", day, month, year);
                Toast.makeText(MainActivity.this, dateMessage, Toast.LENGTH_SHORT).show();
                resultTextView.setText(dateMessage);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(MainActivity.this, "All inputs must be integer numbers.", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValidDay(int day) {
        return day >= 1 && day <= 31;
    }

    public static boolean isValidMonth(int month) {
        return month >= 1 && month <= 12;
    }

    public static boolean isValidYear(int year) {
        return year >= 1000 && year <= 3000;
    }

    public static boolean isValidDate(int day, int month, int year) {
        if (year < 1900 || year > 2100) {
            return false;
        }
        if (month < 1 || month > 12) {
            return false;
        }
        return day >= 1 && day <= DaysInMonth(month, year);
    }

    public static int DaysInMonth(int month, int year) {
        if (month < 0 || month > 12 || year < 999 || year > 3001) return 0;
        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        if (month == 2 && isLeapYear(year)) {
            daysInMonth[1] = 29;
        }
        return daysInMonth[month - 1];
    }

    public static boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }

    private void clearFields() {
        dayInput.setText("");
        monthInput.setText("");
        yearInput.setText("");
        resultTextView.setText("");
    }
}
