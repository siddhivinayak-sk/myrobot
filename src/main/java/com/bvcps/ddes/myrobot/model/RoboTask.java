/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bvcps.ddes.myrobot.model;

/**
 * This class refers to the Java Bean for real Task. It contains information about
 * a task, which collectively build a test case.
 * @author sandeepkumar
 */
public class RoboTask {
    private Long id;
    private String description;
    private String action;
    private String selectBy;
    private String elementId;
    private String data;
    private String waitTime;
    private String expectedCondition;
    private String waitSelectBy;
    private String waitElementId;
    private String repeatTimes;
    private String result;

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
     * Return task description
     * @return Return value
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set Task description
     * @param description Input parameter
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return Task action
     * @return Return value
     */
    public String getAction() {
        return action;
    }

    /**
     * Set Task action
     * @param action Input parameter
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Return Task selectBy flag
     * @return Return value
     */
    public String getSelectBy() {
        return selectBy;
    }

    /**
     * Set Task selectBy flag
     * @param selectBy Input parameter
     */
    public void setSelectBy(String selectBy) {
        this.selectBy = selectBy;
    }

    /**
     * Return Task selected element id
     * @return Return value
     */
    public String getElementId() {
        return elementId;
    }

    /**
     * Set Task selected element id
     * @param elementId Input parameter
     */
    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    /**
     * Return Task data
     * @return Return value
     */
    public String getData() {
        return data;
    }

    /**
     * Set Task data
     * @param data Input parameter
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Return Task waitTime
     * @return Return value
     */
    public String getWaitTime() {
        return waitTime;
    }

    /**
     * Set Task waitTime
     * @param waitTime Input parameter
     */
    public void setWaitTime(String waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * Return Task expected condition
     * @return Return value
     */
    public String getExpectedCondition() {
        return expectedCondition;
    }

    /**
     * Set Task expected condition
     * @param expectedCondition Input parameter
     */
    public void setExpectedCondition(String expectedCondition) {
        this.expectedCondition = expectedCondition;
    }

    /**
     * Return Task wait selectBy
     * @return Return value
     */
    public String getWaitSelectBy() {
        return waitSelectBy;
    }

    /**
     * Set Task wait selectBy
     * @param waitSelectBy Input parameter
     */
    public void setWaitSelectBy(String waitSelectBy) {
        this.waitSelectBy = waitSelectBy;
    }

    /**
     * Return Task wait element id
     * @return Return value
     */
    public String getWaitElementId() {
        return waitElementId;
    }

    /**
     * Set Task wait element id
     * @param waitElementId Input parameter
     */
    public void setWaitElementId(String waitElementId) {
        this.waitElementId = waitElementId;
    }

    /**
     * Return Task repeat times value
     * @return Return value
     */
    public String getRepeatTimes() {
        return repeatTimes;
    }

    /**
     * Set Task repeat times value
     * @param repeatTimes Input parameter
     */
    public void setRepeatTimes(String repeatTimes) {
        this.repeatTimes = repeatTimes;
    }

    /**
     * Return Task result
     * @return Return value
     */
    public String getResult() {
        return result;
    }

    /**
     * Set Task result
     * @param result Input parameter
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Override toString() method
     * @return Return value
     */
    @Override
    public String toString() {
        return "RoboTask{" + "id=" + id + ", description=" + description + ", action=" + action + ", selectBy=" + selectBy + ", elementId=" + elementId + ", data=" + data + ", waitTime=" + waitTime + ", expectedCondition=" + expectedCondition + ", waitSelectBy=" + waitSelectBy + ", waitElementId=" + waitElementId + ", repeatTimes=" + repeatTimes + ", result=" + result + '}';
    }
}
