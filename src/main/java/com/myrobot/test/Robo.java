/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.test;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.myrobot.model.RoboTest;

/**
 * This is the super class for Test. It contains driver information and RoboTest
 * object to perform Test.
 * @author sandeepkumar
 */
public class Robo {
    public Logger logger = Logger.getLogger(Robo.class);
    public WebDriver webDriver;
    public RoboTest test;

    /**
     * Return WebDriver
     * @return Return value
     */
    public WebDriver getWebDriver() {
        return webDriver;
    }

    /**
     * Set WebDriver
     * @param webDriver Input parameter
     */
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * Return object of RoboTest
     * @return Return value
     */
    public RoboTest getTest() {
        return test;
    }

    /**
     * Set RoboTest object
     * @param test Input parameter
     */
    public void setTest(RoboTest test) {
        this.test = test;
    }
    
    /**
     * Before Advice for Test
     */
    @BeforeClass
    public void beforeRobo() {
        //System.out.println("Before Robo");
    }

    /**
     * After advice for Test
     */
    @AfterClass
    public void afterRobo() {
        //System.out.println("After Robo");
    }
    
}
