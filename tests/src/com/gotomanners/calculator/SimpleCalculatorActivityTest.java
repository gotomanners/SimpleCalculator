package com.gotomanners.calculator;

import android.test.ActivityInstrumentationTestCase2;
import android.test.FlakyTest;
import android.test.suitebuilder.annotation.LargeTest;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.gotomanners.calculator.SimpleCalculatorActivityTest \
 * com.gotomanners.calculator.tests/android.test.InstrumentationTestRunner
 */
public class SimpleCalculatorActivityTest extends ActivityInstrumentationTestCase2<SimpleCalculatorActivity> {

    private SimpleCalculatorActivity mActivity;
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

    private String resourceString;

    private ArrayList<Button> button_numbers_array;
    private ArrayList<Button> button_operations_array;

    private static final int SLEEP_TIME = 500;

    public SimpleCalculatorActivityTest() {
        super("com.gotomanners.calculator", SimpleCalculatorActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = this.getActivity();
        calcOutput = (TextView) mActivity.findViewById(R.id.calcOutput);
        resourceString = mActivity.getString(R.string.calc_output_value);

        button_numbers_array = new ArrayList<Button>();
        button_operations_array = new ArrayList<Button>();

        button0 = (Button) mActivity.findViewById(R.id.button0);
        button_numbers_array.add(button0);
        button1 = (Button) mActivity.findViewById(R.id.button1);
        button_numbers_array.add(button1);
        button2 = (Button) mActivity.findViewById(R.id.button2);
        button_numbers_array.add(button2);
        button3 = (Button) mActivity.findViewById(R.id.button3);
        button_numbers_array.add(button3);
        button4 = (Button) mActivity.findViewById(R.id.button4);
        button_numbers_array.add(button4);
        button5 = (Button) mActivity.findViewById(R.id.button5);
        button_numbers_array.add(button5);
        button6 = (Button) mActivity.findViewById(R.id.button6);
        button_numbers_array.add(button6);
        button7 = (Button) mActivity.findViewById(R.id.button7);
        button_numbers_array.add(button7);
        button8 = (Button) mActivity.findViewById(R.id.button8);
        button_numbers_array.add(button8);
        button9 = (Button) mActivity.findViewById(R.id.button9);
        button_numbers_array.add(button9);
        button_decimal_point = (Button) mActivity.findViewById(R.id.button_decimal_point);
        button_numbers_array.add(button_decimal_point);

        button_operator_add = (Button) mActivity.findViewById(R.id.button_operator_add);
        button_operations_array.add(button_operator_add);
        button_operator_subtract = (Button) mActivity.findViewById(R.id.button_operator_subtract);
        button_operations_array.add(button_operator_subtract);
        button_operator_multiply = (Button) mActivity.findViewById(R.id.button_operator_multiply);
        button_operations_array.add(button_operator_multiply);
        button_operator_divide = (Button) mActivity.findViewById(R.id.button_operator_divide);
        button_operations_array.add(button_operator_divide);

        button_clear = (Button) mActivity.findViewById(R.id.button_clear);
        button_operator_equals = (Button) mActivity.findViewById(R.id.button_operator_equals);

        mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        mActivity.resetCalculator();
                    }
                }
        );
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    @SmallTest
    public void testPreConditions() {
        assertTrue(calcOutput != null);

        assertNotNull(button_numbers_array);
        for(Button aButton : button_numbers_array) {
            assertTrue(aButton != null);
        }
        assertNotNull(button_operations_array);
        for(Button aButton : button_operations_array) {
            assertTrue(aButton != null);
        }
    }

    @SmallTest
    public void testCalcOutputText() {
        mActivity = getActivity();
        calcOutput = (TextView) mActivity.findViewById(R.id.calcOutput);
        assertTrue(resourceString.equals(calcOutput.getText().toString().trim()));
    }

    @FlakyTest(tolerance = 3)
    @MediumTest
    public void testUINumberButtonsClicks() throws Exception {
        mActivity = getActivity();
        int i = 0;
        assertTrue(button_numbers_array.size() > 0);
        for(final Button numButton : button_numbers_array) {
            mActivity = getActivity();
            mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        numButton.requestFocus();
                        numButton.performClick();
                    }
                }
            );
            mActivity = getActivity();
            Thread.sleep(SLEEP_TIME);

            calcOutput = (TextView) mActivity.findViewById(R.id.calcOutput);
            if(i != button_numbers_array.size()-1) {
                assertTrue(String.valueOf(i).equals(calcOutput.getText().toString().trim()));
            } else {
                assertTrue(String.valueOf(".").equals(calcOutput.getText().toString().trim()));
            }
            i++;
        }
    }

    @FlakyTest(tolerance = 3)
    @MediumTest
    public void testUIOperationsButtonsClicks() throws Exception {
        mActivity = getActivity();
        int i = 0;
        assertTrue(button_operations_array.size() > 0);
        for(final Button operButton : button_operations_array) {
            mActivity = getActivity();
            mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        operButton.requestFocus();
                        operButton.performClick();
                    }
                }
            );
            mActivity = getActivity();
            Thread.sleep(SLEEP_TIME);

//            assertTrue(String.valueOf(operButton.getText()).equals(mActivity.getCalculatorLogic().operationsEntered.get(i)));
            i++;
        }
    }

    public void testUIEqualsButtonClick() throws Exception {
        mActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        try {
                            button1 = (Button) mActivity.findViewById(R.id.button1);
                            button2 = (Button) mActivity.findViewById(R.id.button2);
                            button9 = (Button) mActivity.findViewById(R.id.button9);
                            button_operator_add = (Button) mActivity.findViewById(R.id.button_operator_add);
                            button_operator_equals = (Button) mActivity.findViewById(R.id.button_operator_equals);

                            button9.requestFocus();
                            button9.performClick();
                            button_operator_add.requestFocus();
                            button_operator_add.performClick();
                            Thread.sleep(SLEEP_TIME);
                            button1.requestFocus();
                            button1.performClick();
                            Thread.sleep(SLEEP_TIME);
                            button2.requestFocus();
                            button2.performClick();
                            Thread.sleep(SLEEP_TIME);
                            button_operator_equals.requestFocus();
                            button_operator_equals.performClick();
                            Thread.sleep(SLEEP_TIME);
                            assertEquals("21", calcOutput.getText().toString().trim());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    public void testUIClearButtonClick() throws Exception {

        mActivity.runOnUiThread(
        new Runnable() {
                public void run() {
                    try {
                        button1 = (Button) mActivity.findViewById(R.id.button1);
                        button2 = (Button) mActivity.findViewById(R.id.button2);
                        button8 = (Button) mActivity.findViewById(R.id.button8);
                        button_operator_add = (Button) mActivity.findViewById(R.id.button_operator_add);
                        button_operator_equals = (Button) mActivity.findViewById(R.id.button_operator_equals);

                        button8.requestFocus();
                        button8.performClick();
                        Thread.sleep(SLEEP_TIME);
                        button_operator_add.requestFocus();
                        button_operator_add.performClick();
                        Thread.sleep(SLEEP_TIME);
                        button1.requestFocus();
                        button1.performClick();
                        Thread.sleep(SLEEP_TIME);
                        button2.requestFocus();
                        button2.performClick();
                        Thread.sleep(SLEEP_TIME);
                        button_operator_equals.requestFocus();
                        button_operator_equals.performClick();
                        Thread.sleep(SLEEP_TIME);

                        assertEquals("20", calcOutput.getText().toString().trim());

                        button_clear.requestFocus();
                        button_clear.performClick();
                        Thread.sleep(SLEEP_TIME);

                        assertEquals("0", calcOutput.getText().toString().trim());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
    }

}
