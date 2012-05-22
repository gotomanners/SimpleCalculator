package com.gotomanners.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimpleCalculatorActivity extends Activity implements View.OnClickListener {

private static final String LOG_TAG = "SimpleCalculator";
private EditText calcOutput;
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

private String lastNumber;
private String lastOperation;



/**
 * Called when the activity is first created.
 */
@Override
public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.main);

	calcOutput = (EditText) this.findViewById(R.id.calcOutput);

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

}

private void appendNumberPressedToCalcOutput(View view) {
	if(calcOutput.getText().toString().equals("0")) {
		calcOutput.setText(((Button) view).getText());
	} else {
		calcOutput.append(((Button)view).getText());
	}
}

private void clearOutputText(View view) {
	calcOutput.setText("0");
    this.lastNumber = "";
    this.lastOperation = "";
}

private void recordNumberPressed(View view) {
	this.lastNumber = ((Button) view).getText().toString();
}

private void recordOperationPressed(final View view) {
	this.lastNumber = calcOutput.getText().toString();
	this.lastOperation = ((Button) view).getText().toString();

	clearOutputText(view);
//    calcOutput.setText(this.lastNumber+this.lastOperation);
}

private String performCalcOperation(View view, String operation) {
	if(operation != null) {
		try{
            double valueOfPreviousTextInOutput = Double.parseDouble(this.lastNumber);
            double valueOfCurrentTextInOutput = Double.parseDouble(calcOutput.getText().toString());
            double result = 0;

            if(operation.equals("/")) {
                if(valueOfCurrentTextInOutput == 0.0) {
                    Toast.makeText(view.getContext(),getString(R.string.error_divide_b_zero), Toast.LENGTH_SHORT).show();
                    return getString(R.string.error_error_string);
                }
                result = (valueOfPreviousTextInOutput / valueOfCurrentTextInOutput);
            }
            if(operation.equals("*")) {
                result = (valueOfPreviousTextInOutput * valueOfCurrentTextInOutput);
            }
            if(operation.equals("-")) {
                result = (valueOfPreviousTextInOutput - valueOfCurrentTextInOutput);
            }
            if(operation.equals("+")) {
                result = (valueOfPreviousTextInOutput + valueOfCurrentTextInOutput);
            }

            String textResult = String.valueOf(result);
            if(textResult.endsWith(".0")) {
                textResult = String.valueOf(Math.round(result));
            }

            return textResult;
        } catch (Exception e) {
            Toast.makeText(view.getContext(),e.getMessage(), Toast.LENGTH_LONG).show();
            Log.v(LOG_TAG, e.getMessage());
        }
	}
    return getString(R.string.error_error_string);
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
			recordOperationPressed(view);
			break;
		case R.id.button_clear: {
			clearOutputText(view);
			break;
		}
		case R.id.button_operator_equals: {
			String result = performCalcOperation(view, this.lastOperation);
            calcOutput.setText(result);
			break;
		}
	}
}
}
