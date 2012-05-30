package com.gotomanners.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SimpleCalculatorActivity extends Activity implements View.OnClickListener {

    protected static final String LOG_TAG = "SimpleCalculator";
    private TextView calcOutput;
    private Button button0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private Button button_clear;
    private Button button_decimal_point;

    private Button button_operator_divide;
    private Button button_operator_multiply;
    private Button button_operator_subtract;
    private Button button_operator_add;

    private Button button_operator_equals;

    private CalculatorLogic calculatorLogic;

    public CalculatorLogic getCalculatorLogic() {
        return calculatorLogic;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        calcOutput = (TextView) this.findViewById(R.id.calcOutput);

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button_clear = (Button) findViewById(R.id.button_clear);
        button_decimal_point = (Button) findViewById(R.id.button_decimal_point);

        button_operator_divide = (Button) findViewById(R.id.button_operator_divide);
        button_operator_multiply = (Button) findViewById(R.id.button_operator_multiply);
        button_operator_subtract = (Button) findViewById(R.id.button_operator_subtract);
        button_operator_add = (Button) findViewById(R.id.button_operator_add);
        button_operator_equals = (Button) findViewById(R.id.button_operator_equals);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button_clear.setOnClickListener(this);
        button_decimal_point.setOnClickListener(this);

        button_operator_divide.setOnClickListener(this);
        button_operator_multiply.setOnClickListener(this);
        button_operator_subtract.setOnClickListener(this);
        button_operator_add.setOnClickListener(this);
        button_operator_equals.setOnClickListener(this);

        calculatorLogic = new CalculatorLogic();
        CalculatorLogic.setContext(this);
        calculatorLogic.setLastNumber("");

    }

    private void appendNumberPressedToCalcOutput(View view) {
        if(calculatorLogic.shouldResetAfterResult()) {
            resetCalculator();
            calculatorLogic.setShouldResetAfterResult(false);
        }
        String numPressed = ((Button) view).getText().toString();
        if (calcOutput.getText().toString().equals("0")) {
            calcOutput.setText(numPressed);
        } else {
            calcOutput.append(numPressed);
        }

        calculatorLogic.setLastNumber(calculatorLogic.getLastNumber()+numPressed);
        calculatorLogic.setLooseOperatorPresent(false);

        Log.i(LOG_TAG, "appended= "+calculatorLogic.getLastNumber());
    }

    private void appendOperationPressedToCalcOutput(View view) {
        if(!calculatorLogic.isLooseOperatorPresent()) {
            Log.i(LOG_TAG, "No immediate existing operator");
            String operation = ((Button) view).getText().toString();
            calcOutput.append(operation);
            recordOperationPressed(view);
            calculatorLogic.setLooseOperatorPresent(true);
            calculatorLogic.setShouldResetAfterResult(false);

            Log.i(LOG_TAG, "Appending "+((Button) view).getText());
        }
    }

    protected void resetCalculator() {
        calcOutput.setText("0");
        calculatorLogic.clearAll();

        Log.i(LOG_TAG, "cleared output");
    }

    private void recordNumberEntered() {
        calculatorLogic.getNumbersEntered().add(calculatorLogic.getLastNumber());
        Log.i(LOG_TAG, "last number recorded= "+calculatorLogic.getLastNumber());

        calculatorLogic.setLastNumber("");
        calculatorLogic.setLooseOperatorPresent(false);
    }

    private void recordOperationPressed(View view) {
        String op = ((Button) view).getText().toString();

        recordNumberEntered();
        calculatorLogic.getOperationsEntered().add(op);

        Log.i(LOG_TAG, "last operation recorded= " + op);
    }

    private void displayResult() {
        String result = removeUnnecessaryDecimalZero(calculatorLogic.getTotal());
        calcOutput.setText(result);

        Log.i(LOG_TAG, "Displaying Result " + result);
    }

    private void performCalculation(View view) {
        Log.i(LOG_TAG, "Performing Calculation");

        try {
            recordNumberEntered();

            double result = calculatorLogic.solve();
            calculatorLogic.setLastNumber(removeUnnecessaryDecimalZero(result));

            displayResult();

            calculatorLogic.clearAll();
            calculatorLogic.setShouldResetAfterResult(true);

        } catch (CannotDivideByZeroException cdz) {
            Toast.makeText(this, getString(R.string.error_divide_by_zero), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, cdz.getMessage());
            resetCalculator();
        } catch (Exception e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e(LOG_TAG, e.getMessage());
            resetCalculator();
        }
        calculatorLogic.setLooseOperatorPresent(false);
    }

    private String removeUnnecessaryDecimalZero(double number) {
        String textResult = String.valueOf(number);
        if (textResult.endsWith(".0")) {
            textResult = String.valueOf(Math.round(number));
        }
        return textResult;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
            case R.id.button_decimal_point:
                appendNumberPressedToCalcOutput(view);
                break;
            case R.id.button_operator_divide:
            case R.id.button_operator_multiply:
            case R.id.button_operator_subtract:
            case R.id.button_operator_add:
                appendOperationPressedToCalcOutput(view);
                break;
            case R.id.button_clear: {
                resetCalculator();
                break;
            }
            case R.id.button_operator_equals: {
                performCalculation(view);
                break;
            }
        }
    }
}
