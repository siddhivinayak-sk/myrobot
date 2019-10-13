/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.listeners;

import com.myrobot.model.RoboSuit;
import com.myrobot.model.RoboTest;
import com.myrobot.utils.ExcelUtil;

import java.io.File;
import java.util.List;
import java.util.Map;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.apache.log4j.Logger;
import org.testng.reporters.Files;

/**
 * This is a listener class which intercepts events from the 
 * Suit(s), Case(s) and Test(s) and perform the defined task for each and 
 * every event propagate.
 * @author sandeepkumar
 * @version 1.0
 */
public class RobotListener extends TestListenerAdapter {
    private static Logger logger = Logger.getLogger(RobotListener.class);
    
    /**
     * This method executes when test completed successfully and success event propagated
     * @param tr ITestResult object is passed with event
     */
    @Override
    public void onTestSuccess(ITestResult tr) {
        //System.out.println("onTestSuccess");
        super.onTestSuccess(tr);
    }

    /**
     * This method executes when test failed and fail event propagated
     * @param tr ITestResult object is passed with event
     */
    @Override
    public void onTestFailure(ITestResult tr) {
        //System.out.println("onTestFailure");
        super.onTestFailure(tr);
    }

    /**
     * This method executes when test skipped and skip event propagated
     * @param tr ITestResult object is passed with event
     */
    @Override
    public void onTestSkipped(ITestResult tr) {
        //System.out.println("onTestSkipped");
        super.onTestSkipped(tr);
    }

    /**
     * This method executes when test failed and with some success percentage
     * @param tr ITestResult object is passed with event
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
        //System.out.println("onTestFailedButWithinSuccessPercentage");
        super.onTestFailedButWithinSuccessPercentage(tr);
    }

    /**
     * This method executes when Suit starts execution
     * @param testContext ITestContext object is passed with event
     */
    @Override
    public void onStart(ITestContext testContext) {
        //System.out.println("onStart");
        super.onStart(testContext);
    }

    /**
     * This method executes when Suit finishes execution
     * @param testContext ITestContext object is passed with event
     */
    @Override
    public void onFinish(ITestContext testContext) {
        //System.out.println("onFinish");
        super.onFinish(testContext);
    }

    /**
     * This method executes when any Test start execution
     * @param result ITestResult object is passed with event 
     */
    @Override
    public void onTestStart(ITestResult result) {
        //System.out.println("onTestStart");
        super.onTestStart(result);
    }

    /**
     * This method executes when configuration failure event occurs
     * @param itr ITestResult object is passed with event
     */
    @Override
    public void onConfigurationFailure(ITestResult itr) {
        //System.out.println("onConfigurationFailure");
        super.onConfigurationFailure(itr);
    }

    /**
     * This method executes before configuration starts
     * @param tr ITestResult object is passed with event
     */
    @Override
    public void beforeConfiguration(ITestResult tr) {
        //System.out.println("beforeConfiguration");
        super.beforeConfiguration(tr);
    }

    /**
     * This method executes when configuration skipped event occurs
     * @param itr ITestResult object is passed with event
     */
    @Override
    public void onConfigurationSkip(ITestResult itr) {
        //System.out.println("onConfigurationSkip");
        super.onConfigurationSkip(itr);
    }

    /**
     * This method executes when configuration success event occurs
     * @param itr ITestResult object is passed with event
     */
    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        //System.out.println("onConfigurationSuccess");
        super.onConfigurationSuccess(itr);
    }
}
