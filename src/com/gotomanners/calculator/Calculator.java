package com.gotomanners.calculator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: moshafi
 * Date: 5/23/12
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class Calculator {

    private static final String LOG_TAG = "SimpleCalculator";
    private static Context context = null;
    double total;
    ArrayList<String> numbersEntered;
    ArrayList<String> operationsEntered;

    public Calculator() {
        this.total = 0.0;
        numbersEntered = new ArrayList<String>();
        operationsEntered = new ArrayList<String>();
    }

    public static void setContext(Context context) {
        Calculator.context = context;
    }

    protected double solve() throws Exception {
        if(numbersEntered != null && operationsEntered != null) {
            if(numbersEntered.size() > 0 && operationsEntered.size() > 0) {
                if((numbersEntered.size() - 1) == operationsEntered.size()) {
                    String number;
                    String operation = "";
                    for (int i=0; i<numbersEntered.size(); i++) {
                        number = numbersEntered.get(i);
                        if(i ==0){
                            setTotal(stringToDouble(number));
                        } else {
                            operation = operationsEntered.get(i-1);
                            if(operation.equals("+")) {
                                this.add(number);
                            }
                            if(operation.equals("-")) {
                                this.subtract(number);
                            }
                            if(operation.equals("*")) {
                                this.multiply(number);
                            }
                            if(operation.equals("/")) {
                                if(number.equals("0")) {
                                    clearAll();
                                    throw new CannotDivideByZeroException(context.getString(R.string.error_divide_by_zero));
//                                    return 0.0;
                                }
                                this.divide(number);
                            }
                        }
                    }
                } else {
                    System.out.println("numbers and operations size do not match!");
                    return 0.0;
                }
            }
        }
        return this.total;
    }

    private double add(String num) {

        return this.total += stringToDouble(num);
    }

    private double subtract(String num) {

        return this.total -= stringToDouble(num);
    }

    private double multiply(String num) {

        return this.total *= stringToDouble(num);
    }

    private double divide(String num) {

        return this.total /= stringToDouble(num);
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    protected void clearAll() {
        setTotal(0.0);
        numbersEntered.clear();
        operationsEntered.clear();
    }

    private double stringToDouble(String number) {
        return Double.parseDouble(number);
    }

    private String doubleToString(double number) {
        return String.valueOf(number);
    }
}

class CannotDivideByZeroException extends Exception {
    CannotDivideByZeroException() {
        super();
    }

    CannotDivideByZeroException(String detailMessage) {
        super(detailMessage);
    }
}
