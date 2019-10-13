/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.test;

import java.util.ArrayList;
import java.util.List;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

/**
 * This class extends Robo class and contains advice for Suits.
 * @author sandeepkumar
 */
public class Robot extends Robo {
    /**
     * Before advice for Test Suit
     */
    @BeforeSuite
    public void beforeRobot() {
        System.out.println("Before Robot");
    }

    /**
     * After advice for Test Suit
     */
    @AfterSuite
    public void afterRobot() {
        System.out.println("After Robot");
    }
    
//    @DataProvider
//    public Object[][] dataProvider() {
//        System.out.println("Data Provider");
//        return new Object[1][1];
//    }
}
