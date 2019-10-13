/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bvcps.ddes.myrobot.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a Java Bean class which represents an actual Suit. It contains all 
 * the information about suit along with list of test cases.
 * @author sandeepkumar
 */
public class RoboSuit {
    private static List<RoboSuit> roboSuitList;

    private Long id;
    private String name;
    private String description;
    private Boolean parallel;
    private Boolean isRun;
    private String result;
    private List<RoboTest> testList;

    /**
     * Return Suit name
     * @return Return value
     */
    public String getName() {
        return name;
    }

    /**
     * Set Suit name
     * @param name Input parameter
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return parallel information
     * @return Return value
     */
    public Boolean getParallel() {
        return parallel;
    }

    /**
     * Set parallel information
     * @param parallel Input parameter
     */
    public void setParallel(Boolean parallel) {
        this.parallel = parallel;
    }

    /**
     * Return Test list
     * @return Return value
     */
    public List<RoboTest> getTestList() {
        return testList;
    }

    /**
     * Set test list
     * @param testList Input parameter
     */
    public void setTestList(List<RoboTest> testList) {
        this.testList = testList;
    }

    /**
     * Return Suit result
     * @return Return value
     */
    public String getResult() {
        return result;
    }

    /**
     * Set Suit result
     * @param result Input parameter
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Return unique Id
     * @return Return value
     */
    public Long getId() {
        return id;
    }

    /**
     * Set unique Id
     * @param id Input parameter
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Return Suit description
     * @return Return value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set Suit description
     * @param description Input parameter
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return Suit isRun flag
     * @return Return value
     */
    public Boolean getIsRun() {
        return isRun;
    }

    /**
     * Set Suit isRun flag
     * @param isRun Input parameter
     */
    public void setIsRun(Boolean isRun) {
        this.isRun = isRun;
    }
    
    /**
     * Factory method for List&lt;RoboSuit&gt;. It creates new list only when list is null.
     * @return Return value
     */
    public static List<RoboSuit> getRoboSuitList() {
        if(null == roboSuitList) {
            roboSuitList = new ArrayList<>();
        }
        return roboSuitList;
    }

    /**
     * Factory method for List&lt;RoboSuit&gt;. It always create new list.
     * @return Return value
     */
    public static List<RoboSuit> getDefaultRoboSuitList() {
        roboSuitList = new ArrayList<>();
        return roboSuitList;
    }

    /**
     * Set RoboSuit list
     * @param roboSuitList Input parameter
     */
    public static void setRoboSuitList(List<RoboSuit> roboSuitList) {
        RoboSuit.roboSuitList = roboSuitList;
    }
    
    
}
