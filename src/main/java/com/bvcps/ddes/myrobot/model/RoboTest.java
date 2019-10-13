/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bvcps.ddes.myrobot.model;

import java.util.List;

/**
 * This class represents Java Bean of real Test entity. It contains information
 * about a single Test along with the steps/tasks for the particular Test.
 * @author sandeepkumar
 */
public class RoboTest {
    private Long id;
    private String name;
    private String suitName;
    private String stepSheetName;
    private String className;
    private Boolean isRun;
    private String result;
    private List<RoboTask> taskList;

    /**
     * Returns Test name
     * @return Return value
     */
    public String getName() {
        return name;
    }

    /**
     * Set Test name
     * @param name Input parameter
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns Test's tasks list
     * @return Return value
     */
    public List<RoboTask> getTaskList() {
        return taskList;
    }

    /**
     * Set Test's tasks list
     * @param taskList Input parameter
     */
    public void setTaskList(List<RoboTask> taskList) {
        this.taskList = taskList;
    }

    /**
     * Returns Test result
     * @return Return value
     */
    public String getResult() {
        return result;
    }

    /**
     * Set Test result
     * @param result Input parameter
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Return unique id
     * @return Return value
     */
    public Long getId() {
        return id;
    }

    /**
     * Set unique id
     * @param id Input parameter
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Return Suit name for the Test
     * @return Return value
     */
    public String getSuitName() {
        return suitName;
    }

    /**
     * Set Suit name for the Test
     * @param suitName Input parameter
     */
    public void setSuitName(String suitName) {
        this.suitName = suitName;
    }

    /**
     * Returns Test step sheet name
     * @return Return value
     */
    public String getStepSheetName() {
        return stepSheetName;
    }

    /**
     * Set Test step sheet name
     * @param stepSheetName Input parameter
     */
    public void setStepSheetName(String stepSheetName) {
        this.stepSheetName = stepSheetName;
    }

    /**
     * Return Test class name
     * @return Return value
     */
    public String getClassName() {
        return className;
    }

    /**
     * Set Test class name
     * @param className Input parameter
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * Return Test isRun flag
     * @return Return value
     */
    public Boolean getIsRun() {
        return isRun;
    }

    /**
     * Set Test isRun flag
     * @param isRun Input parameter
     */
    public void setIsRun(Boolean isRun) {
        this.isRun = isRun;
    }
}
