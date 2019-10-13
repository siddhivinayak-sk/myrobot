/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.test;

import com.myrobot.model.RoboSuit;
import com.myrobot.model.RoboTest;
import com.myrobot.utils.ExcelUtil;
import com.myrobot.utils.LoadDriver;

import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

/**
 * This class extends Robot class so can access driver and RoboTest information.
 * This class contains advice for Test, thus, used to configure web driver and
 * also used to close driver when test completes.
 * @author sandeepkumar
 */
public class RobotBase extends Robot {
   
    /**
     * Before advice for Test
     * @param testContext Input parameter
     * @throws Exception Throws runtime exception
     */
    @BeforeTest
    public void beforeTest(final ITestContext testContext) throws Exception {
        RoboSuit suit = null;
        RoboTest test = null;
        String suitName = testContext.getSuite().getName();
        String testName = testContext.getName();
        List<RoboSuit> suitList = RoboSuit.getRoboSuitList();
        if(null == suitList || suitList.size() <= 0) {
            throw new Exception("There is no suit available to execute");
        }
        for(RoboSuit temp:suitList) {
            if(null != suitName && suitName.equalsIgnoreCase(temp.getName())) {
                suit = temp;
                break;
            }
        }
        if(null == suit || null == suit.getTestList() || suit.getTestList().size() <= 0) {
            throw new Exception("Invalid Suit " + suitName);
        }
        for(RoboTest temp:suit.getTestList()) {
            if(null != testName && testName.equalsIgnoreCase(temp.getName())) {
                test = temp;
                break;
            }
        }
        if(null == test || null == test.getTaskList() || test.getTaskList().size() <= 0) {
            throw new Exception("Invalid Test" + testName);
        }
        if(null != webDriver) {
            webDriver.quit();
        }
        try {
            webDriver = LoadDriver.createWebDriver();
        }
        catch(Exception e) {
            logger.error("Error in loading driver", e);
            test.setResult("Exception Occured!");
            throw e;
        }
        this.test = test;
    }

    /**
     * After advice for Test
     */
    @AfterTest
    public void afterTest() {
        ExcelUtil.writeResult(test);
        if(null != webDriver) {
            webDriver.quit();
        }
    }

    /**
     * Before advice for Test methods
     */
    @BeforeMethod
    public void beforeMethod() {
        //System.out.println("Before Method");
    }

    /**
     * After advice for Test methods
     * @param result Input parameter
     */
    @AfterMethod
    public void afterMethod(ITestResult result) {
        //System.out.println("After Test");
    }
}
