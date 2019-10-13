/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.test;

import com.myrobot.model.RoboTask;
import com.myrobot.model.RoboTest;
import com.myrobot.utils.WebUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.testng.annotations.Test;

/**
 * This class extends RobotBase, it means it has all the RoboTest information
 * along with prepared web driver and advice. This actually contains the test
 * method with Test annotation and it performs all the tasks needed for any
 * particular test. It contains methods which iterates the list of tasks
 * and run one by one in sequenced manner. If any task fails, an exception is
 * generated and propagated to parent environment, which trace up and reached to
 * the Test API and fails the complete test.
 * @author sandeepkumar
 */
public class RobotTest extends RobotBase {
    
//    @Test(dataProvider = "dataProvider")
//    public void test(Object[][] data) {
//        System.out.println("Test");
//    }

    /**
     * This method executes and initiate processing of task list by invoking
     * subsequent methods. 
     * @throws Exception Throws runtime exception
     */
    @Test
    public void test() throws Exception {
        if(null == test || null == test.getName() || null == test.getClassName() || null == test.getTaskList() || test.getTaskList().size() <= 0) {
            throw new Exception("Error: Test is not correct");
        }
        processTasks(test);
    }
    
    /**
     * This method aquire RoboTest and start processing of task list
     * @param test Input parameter
     * @throws Exception Throws runtime exception
     */
    public void processTasks(RoboTest test) throws Exception {
        long count = 0;
        List<RoboTask> recordTask = new ArrayList<RoboTask>();
        int repeatation = 0;
        boolean repeat = false;
        for(RoboTask task:test.getTaskList()) {
            try {
                count++;
                task.setId(count);
                if(null != task.getRepeatTimes() && !"".equals(task.getRepeatTimes())) {
                    try {
                        repeatation = Integer.parseInt(task.getRepeatTimes());
                        if(repeatation > 1) {
                            repeat = true;
                        }
                    }
                    catch(NumberFormatException nfe) {
                        logger.error("Repeat Times value is not convertable to number", nfe);
                    }
                }
                if(null != task.getAction() && task.getAction().equalsIgnoreCase("EndRepeat") && repeat) {
                    processRepeatTasks(recordTask, repeatation  - 1);
                    recordTask = new ArrayList<>();
                    repeatation = 0;
                    repeat = false;
                }
                if(repeat) {
                    recordTask.add(task);
                }
                processTask(task);
            }
            catch(Exception e) {
                logger.error("Error: Test: " + test.getName(), e);
                test.setResult("Failed!");
                throw e;
            }
        }
    }

    /**
     * This method is specially handle the situation of repeated task
     * @param recordTask Input parameter
     * @param repeatition Input parameter
     * @throws Exception Throws runtime exception
     */
    public void processRepeatTasks(List<RoboTask> recordTask, int repeatition) throws Exception {
        if(null == recordTask || recordTask.size() <= 0) {
            return;
        }
        for(int i = 0; i < repeatition; i++) {
            for(RoboTask task:recordTask) {
                try {
                    processTask(task);
                }
                catch(Exception e) {
                    logger.error("Error: Process Task 2", e);
                    test.setResult("Failed!");
                    throw e;
                }
            }
        }
    }

    /**
     * This method takes the Task and perform the tasks using task data like
     * action, element id, wait time etc. with the help of web util.
     * @param task Input parameter
     * @throws Exception Throws runtime exception
     */
    public void processTask(RoboTask task) throws Exception {
        WebUtil.processTask(webDriver, task);
    }
}
