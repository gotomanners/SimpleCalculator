package com.gotomanners.calculator;

import android.test.AndroidTestCase;

/**
 * Created with IntelliJ IDEA.
 * User: moshafi
 * Date: 5/30/12
 * Time: 12:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class CalculatorLogicTest extends AndroidTestCase {

    CalculatorLogic calculatorLogic;

    public CalculatorLogicTest() {
        super();
    }

    protected void setUp() throws Exception {
        super.setUp();

        calculatorLogic = new CalculatorLogic();
        CalculatorLogic.setContext(getContext());
    }

    public void testAdd() {
        calculatorLogic.getNumbersEntered().add("3");
        calculatorLogic.getOperationsEntered().add("+");
        calculatorLogic.getNumbersEntered().add("3");
        try {
            calculatorLogic.total = calculatorLogic.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(6.0, calculatorLogic.total);
    }

    public void testSubtract() {
        calculatorLogic.getNumbersEntered().add("2");
        calculatorLogic.getOperationsEntered().add("-");
        calculatorLogic.getNumbersEntered().add("3");
        try {
            calculatorLogic.total = calculatorLogic.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(-1.0, calculatorLogic.total);
    }

    public void testMultiply() {
        calculatorLogic.getNumbersEntered().add("3");
        calculatorLogic.getOperationsEntered().add("*");
        calculatorLogic.getNumbersEntered().add("3");
        try {
            calculatorLogic.total = calculatorLogic.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(9.0, calculatorLogic.total);
    }

    public void testDivide() {
        calculatorLogic.getNumbersEntered().add("10");
        calculatorLogic.getOperationsEntered().add("/");
        calculatorLogic.getNumbersEntered().add("2");
        try {
            calculatorLogic.total = calculatorLogic.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(5.0, calculatorLogic.total);
    }

    public void testCannotDivideByZeroException() {
        calculatorLogic.getNumbersEntered().add("10");
        calculatorLogic.getOperationsEntered().add("/");
        calculatorLogic.getNumbersEntered().add("0");
        try {
            calculatorLogic.total = calculatorLogic.solve();
        } catch (Exception e) {
            assertTrue(e instanceof CannotDivideByZeroException);
        }
    }

    public void testClear() {
        calculatorLogic.getNumbersEntered().add("10");
        calculatorLogic.getOperationsEntered().add("+");
        calculatorLogic.getNumbersEntered().add("2");
        try {
            calculatorLogic.total = calculatorLogic.solve();
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals(2, calculatorLogic.getNumbersEntered().size());
        assertEquals(1, calculatorLogic.getOperationsEntered().size());
        assertEquals(12.0, calculatorLogic.total);

        calculatorLogic.clearAll();

        assertEquals(0, calculatorLogic.getNumbersEntered().size());
        assertEquals(0, calculatorLogic.getOperationsEntered().size());
        assertEquals(0.0, calculatorLogic.total);
    }

}
