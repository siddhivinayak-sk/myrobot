/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.utils;

import java.io.File;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.myrobot.model.RoboTask;

/**
 * This class contains utility methods to perform common tasks on browser by
 * using Selenium API.
 * @author sandeepkumar
 */
public class WebUtil {
    private static Logger logger = Logger.getLogger(WebUtil.class);

    /**
     * This method takes by id and element id according to by information and
     * returns By object.
     * @param by Input parameter
     * @param id Input parameter
     * @return Returns By object
     */
    public static By getBy(String by, String id) {
        By result = null;
        if(null == by) {
            by = "id";
        }
        if(by.equalsIgnoreCase("classname")) {
            result = By.className(id);
        }
        else if(by.equalsIgnoreCase("cssselector")) {
            result = By.cssSelector(id);
        }
        else if(by.equalsIgnoreCase("id")) {
            result = By.id(id);
        }
        else if(by.equalsIgnoreCase("linkText")) {
            result = By.linkText(id);
        }
        else if(by.equalsIgnoreCase("name")) {
            result = By.name(id);
        }
        else if(by.equalsIgnoreCase("partialLinkText")) {
            result = By.partialLinkText(id);
        }
        else if(by.equalsIgnoreCase("tagName")) {
            result = By.tagName(id);
        }
        else if(by.equalsIgnoreCase("xpath")) {
            result = By.xpath(id);
        }
        else {
            result = By.id(id);
        }
        return result;
    }
    
    /**
     * This method takes web driver object, by information and element id and
     * returns selected element. If element not exits throws exception.
     * @param webDriver Input parameter
     * @param by Input parameter
     * @param id Input parameter
     * @return Returns WebElement object
     * @throws Exception Throws runtime exception
     */
    public static WebElement getElement(WebDriver webDriver, String by, String id) throws Exception {
        By byId = getBy(by, id);
        WebElement result = webDriver.findElement(byId);
        return result;
    }

    /**
     * This method takes web driver object, by information and element id and
     * returns list of selected element. If element not exits throws exception.
     * @param webDriver Input parameter
     * @param by Input parameter
     * @param id Input parameter
     * @return Returns List&lt;WebElement&gt;
     * @throws Exception Throws runtime exception
     */
    public static List<WebElement> getElements(WebDriver webDriver, String by, String id) throws Exception {
        By byId = getBy(by, id);
        List<WebElement> result = webDriver.findElements(byId);
        return result;
    }


    /**
     * This method takes web driver object, web element, action and data. It 
     * performs action and returns object as per passed action.
     * @param webDriver Input parameter
     * @param webElement Input parameter
     * @param action Input parameter
     * @param data Input parameter
     * @return Returns Object
     * @throws Exception Throws runtime exception
     */
    public static Object performAction(WebDriver webDriver,
            WebElement webElement,
            String action,
            String data) throws Exception {
        Object ob = null;
        if(null == action) {
            return ob;
        }
        if(action.equalsIgnoreCase("openurl")) {
            webDriver.get(data);
        }
        else if(action.equalsIgnoreCase("navigateurl")) {
            if(null != data && data.equalsIgnoreCase("back")) {
                webDriver.navigate().back();
            }
            else if(null != data && data.equalsIgnoreCase("forward")) {
                webDriver.navigate().forward();
            }
            else if(null != data && data.equalsIgnoreCase("refresh")) {
                webDriver.navigate().refresh();
            }
            else if(null != data && data.startsWith("to_")) {
                webDriver.navigate().to(data.replace("to_", ""));
            }
        }
        else if(action.equalsIgnoreCase("clear")) {
            webElement.clear();
        }
        else if(action.equalsIgnoreCase("click")) {
            webElement.click();
        }
        else if(action.equalsIgnoreCase("getattribute")) {
            ob = webElement.getAttribute(data);
        }
        else if(action.equalsIgnoreCase("getcssvalue")) {
            ob = webElement.getCssValue(data);
        }
        else if(action.equalsIgnoreCase("gettagname")) {
            ob = webElement.getTagName();
        }
        else if(action.equalsIgnoreCase("gettext")) {
            ob = webElement.getText();
        }
        else if(action.equalsIgnoreCase("isdisplayed")) {
            ob = webElement.isDisplayed();
        }
        else if(action.equalsIgnoreCase("isenabled")) {
            ob = webElement.isEnabled();
        }
        else if(action.equalsIgnoreCase("isselected")) {
            ob = webElement.isSelected();
        }
        else if(action.equalsIgnoreCase("submit")) {
            webElement.submit();
        }
        else if(action.equalsIgnoreCase("sendkeys")) {
            webElement.sendKeys(data);
        }
        else if(action.equalsIgnoreCase("clear_sendkeys")) {
            webElement.clear();
            webElement.sendKeys(data);
        }
        else if(action.equalsIgnoreCase("switch_to_frame")) {
            webDriver.switchTo().frame(webElement);
        }
        else if(action.equalsIgnoreCase("switch_to_window")) {
            String currentTab = webDriver.getWindowHandle();
            String nextTab = currentTab;
            String previousTab = currentTab;
            String[] tabList = webDriver.getWindowHandles().toArray(new String[]{});
            if(null == tabList || tabList.length < 2) {
                return ob;
            }
            for(int i = 0; i < tabList.length; i++) {
                if(currentTab.equalsIgnoreCase(tabList[i])) {
                    if(i > 0) {
                        previousTab = tabList[i - 1];
                    }
                    if((i + 1) < tabList.length) {
                        nextTab = tabList[i + 1];
                    }
                }
            }
            if(null != data && "next".equalsIgnoreCase(data)) {
                webDriver.switchTo().window(nextTab);
            }
            else if(null != data && "previous".equalsIgnoreCase(data)) {
                webDriver.switchTo().window(previousTab);
            }
            else if(null != data && data.contains("Tab-")) {
                int moveToTab = 0;
                try {
                    data = data.replace("Tab-", "");
                    moveToTab = Integer.parseInt(data);
                }
                catch(NumberFormatException nfe) {
                }
                if(moveToTab >= 0 && moveToTab < tabList.length) {
                    for(int i = 0; i < tabList.length; i++) {
                        if(i == moveToTab) {
                            webDriver.switchTo().window(tabList[i]);
                            break;
                        }
                    }
                }
            }
        }
        else if(action.equalsIgnoreCase("close")) {
            String currentTab = webDriver.getWindowHandle();
            String nextTab = currentTab;
            String previousTab = currentTab;
            String[] tabList = webDriver.getWindowHandles().toArray(new String[]{});
            String firstTab = (null != tabList && tabList.length > 0)?tabList[0]:currentTab;
            if(null == tabList || tabList.length < 2) {
                return ob;
            }
            for(int i = 0; i < tabList.length; i++) {
                if(currentTab.equalsIgnoreCase(tabList[i])) {
                    if(i > 0) {
                        previousTab = tabList[i - 1];
                    }
                    if((i + 1) < tabList.length) {
                        nextTab = tabList[i + 1];
                    }
                }
            }
            if(null != data && "next".equalsIgnoreCase(data)) {
                webDriver.switchTo().window(nextTab);
            }
            else if(null != data && "previous".equalsIgnoreCase(data)) {
                webDriver.switchTo().window(previousTab);
            }
            else if(null != data && data.contains("Tab-")) {
                int moveToTab = 0;
                try {
                    data = data.replace("Tab-", "");
                    moveToTab = Integer.parseInt(data);
                }
                catch(NumberFormatException nfe) {
                }
                if(moveToTab >= 0 && moveToTab < tabList.length) {
                    for(int i = 0; i < tabList.length; i++) {
                        if(i == moveToTab) {
                            webDriver.switchTo().window(tabList[i]);
                            break;
                        }
                    }
                }
            }
            try {
                webDriver.close();
                //new Actions(webDriver).keyDown(Keys.CONTROL).sendKeys("w").perform();
            }
            catch(Exception e) {
            }
            webDriver.switchTo().window(firstTab);
        }
        else if(action.equalsIgnoreCase("wait")) {
            try {
                if(null == data || "".equals(data)) {
                    data = "1000";
                }
                Thread.sleep(Integer.parseInt(data));
            }
            catch(InterruptedException | NumberFormatException ie) {
            }
        }
        else if(action.toLowerCase().contains("switch_to_alert")) {
            if(action.contains(" ")) {
                String alertAction = action.split(" ")[1];
                Alert alert = webDriver.switchTo().alert();
                if(null != alertAction && alertAction.equalsIgnoreCase("accept")) {
                    alert.accept();
                }
                else if(null != alertAction && alertAction.equalsIgnoreCase("dismiss")) {
                    alert.dismiss();
                }
                else if(null != alertAction && alertAction.equalsIgnoreCase("gettext")) {
                    ob = alert.getText();
                }
                else if(null != alertAction && alertAction.equalsIgnoreCase("sendkeys")) {
                    alert.sendKeys(data);
                }
            }
        }
        
        
        
        return ob;
    }
    
    /**
     * This method takes web driver, select element by, element id, action, data,
     * wait time for element, wait condition, expected condition, wait select by
     * and wait element id. It acquires element/action as per input parameters,
     * perform action and return object as per action.
     * @param webDriver Input parameter
     * @param by Input parameter
     * @param id Input parameter
     * @param action Input parameter
     * @param data Input parameter
     * @param waitTime Input parameter
     * @param expectedCondition Input parameter
     * @param waitSelectBy Input parameter
     * @param waitElementId Input parameter
     * @return Returns Object
     * @throws Exception Throws runtime exception
     */
    public static Object waitAndParformAction(WebDriver webDriver,
            String by,
            String id,
            String action,
            String data,
            String waitTime,
            String expectedCondition,
            String waitSelectBy,
            String waitElementId) throws Exception {
        Object ob = null;
        if(null == waitTime) {
            waitTime = "60";
        }
        if(null == expectedCondition) {
            waitTime = "60";
        }
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, Integer.parseInt(waitTime));
        if(null != expectedCondition && expectedCondition.equalsIgnoreCase("util_visible")) {
            if(null == waitSelectBy || "".equals(waitSelectBy)) {
                waitSelectBy = by;
            }
            if(null == waitElementId || "".equals(waitElementId)) {
                waitElementId = id;
            }
            WebElement waitWebElement = getElement(webDriver, waitSelectBy, waitElementId);
            webDriverWait.until(ExpectedConditions.visibilityOf(waitWebElement));
        }
        else {
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(getBy(waitSelectBy, waitElementId)));
        }
        WebElement webElement = getElement(webDriver, by, id);
        ob = performAction(webDriver, webElement, action, data);
        return ob;
    }
    
    /**
     * This method takes web driver and task object as input and perform task by
     * invoking subsequent method invocation.
     * @param webDriver Input parameter
     * @param task Input parameter
     * @throws Exception Throws runtime exception
     */
    public static void processTask(WebDriver webDriver, RoboTask task) throws Exception {
        try {
            if(null == task || null == task.getAction() || "".equals(task.getAction())) {
                return;
            }
            if(task.getAction().equalsIgnoreCase("opendatasheet")) {
                
            }
            else {
                if(null == task.getExpectedCondition() || "".equals(task.getExpectedCondition())) {
                    WebElement webElement = null;
                    try {
                        webElement = getElement(webDriver, task.getSelectBy(), task.getElementId());
                    }
                    catch(Exception e) {
                        logger.info("Element not found with name: " + task.getElementId());
                    }
                    performAction(webDriver, webElement, task.getAction(), task.getData());
                }
                else {
                    waitAndParformAction(webDriver, task.getSelectBy(), task.getElementId(), task.getAction(), task.getData(), task.getWaitTime(), task.getExpectedCondition(), task.getWaitSelectBy(), task.getWaitElementId());
                }
            }
            task.setResult("Success!");
        }
        catch(Exception e) {
            logger.error("Error RTP: " + task, e);
            task.setResult("Fail!");
            throw e;
        }
        finally {
            taskScreenshot(webDriver);
        }
    }
    
    /**
     * This method is used to take screen shot of current web browser instance
     * and it managed by configuration.
     * @param webDriver Input parameter
     */
    public static void taskScreenshot(WebDriver webDriver) {
        try {
            boolean isScreenShotOn = (null == System.getProperty("driver.screenshot.status") || !"on".equalsIgnoreCase(System.getProperty("driver.screenshot.status")))?false:true;
            if(null == webDriver || !isScreenShotOn) {
                return;
            }
            String outputDirPath = System.getProperty("driver.screenshot.dir");
            if(null == outputDirPath || "".equals(outputDirPath)) {
                outputDirPath = System.getProperty("user.home") + "/RoboTestScreenshots";
            }
            File outputDir = new File(outputDirPath);
            if(!outputDir.exists()) {
                outputDir.mkdirs();
            }
            File srcFile = ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(outputDir.getAbsolutePath() + "/" + System.currentTimeMillis() + ".png"));
        }
        catch(Exception e) {
            logger.error("Error while taking Screenshot:", e);
        }
    }
}
