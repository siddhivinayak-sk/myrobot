/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.reporters.Files;

import com.myrobot.listeners.RobotListener;
import com.myrobot.model.RoboSuit;
import com.myrobot.model.RoboTest;
import com.myrobot.test.RobotTest;
import com.myrobot.utils.ExcelUtil;

/**
 * This class is entry point of MyRobot application. Application enters in
 * main method and start all the suits, cases, and tests. After running all
 * tests, report is generated.
 * @author sandeepkumar
 */
public class Main {
    private static Logger logger = Logger.getLogger(RobotListener.class);
    
    /**
     * This is main method and entry point in application
     * @param args Takes string arguments from command line
     */
    public static void main(String...args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/im", "chromedriver.exe", "/f");
            Process process = processBuilder.start();
            process.waitFor();
            Thread.sleep(1000);
        }
        catch(Exception e) {
        }
        
        try {
            File home = new File("");
            File resources = new File(home.getAbsolutePath() + "/src/main/resources");
            Properties prop = new Properties();
            prop.load(new FileInputStream(resources.getAbsolutePath() + "/application.properties"));
            for(Map.Entry<Object, Object> entry:prop.entrySet()) {
                System.setProperty(entry.getKey().toString(), entry.getValue().toString());
            } 
            List<RoboSuit> inputSuitList = ExcelUtil.scanSuits(System.getProperty("input.excel.dir"));
            StringBuffer testingSuits = new StringBuffer();
            testingSuits.append("<suite name=\"MyRobot Test Suite\">");
            testingSuits.append("\n\t<listeners>");
            testingSuits.append("\n\t\t<listener class-name=\"com.myrobot.listeners.RobotListener\"/>");
            //testingSuits.append("\n\t\t<listener class-name=\"org.uncommons.reportng.HTMLReporter\"/>");
            //testingSuits.append("\n\t\t<listener class-name=\"org.uncommons.reportng.JUnitXMLReporter\"/>");
            testingSuits.append("\n\t</listeners>");
            testingSuits.append("\n\t<suite-files>");
            for(RoboSuit rs:inputSuitList) {
                if(null == rs.getIsRun() || !rs.getIsRun()) {
                    continue;
                }
                if(null != rs && null != rs.getName() && !rs.getName().equals("") && null != rs.getTestList() && rs.getTestList().size() > 0) {
                    testingSuits.append("\n\t\t<suite-file path=\"" + rs.getName() + ".xml\" />");
                    StringBuilder tempSuit = new StringBuilder();
                    tempSuit.append("<suite name=\"" + rs.getName() + "\" parallel=\"" + ((null == rs.getParallel() || !rs.getParallel())?"false":"true") + "\">");
                    for(RoboTest rt:rs.getTestList()) {
                        if(null == rt.getIsRun() || !rt.getIsRun()) {
                            continue;
                        }
                        if(null != rt && null != rt.getStepSheetName() && !rt.getStepSheetName().equals("")) {
                            tempSuit.append("\n\t<test name=\"" + rt.getName() + "\">");
                            tempSuit.append("\n\t\t<classes>");
                            tempSuit.append("\n\t\t\t<class name=\"" + rt.getClassName() + "\"></class>");
                            tempSuit.append("\n\t\t</classes>");
                            tempSuit.append("\n\t</test>");
                        }
                    }
                    tempSuit.append("\n</suite>");
                    Files.writeFile(tempSuit.toString(), new File(resources.getAbsolutePath() + "/" + rs.getName() + ".xml"));
                }
            }
            testingSuits.append("\n\t</suite-files>");
            testingSuits.append("\n</suite>");
            Files.writeFile(testingSuits.toString(), new File(resources.getAbsolutePath() + "/testing.xml"));
            RoboSuit.setRoboSuitList(inputSuitList);
            TestNG runner = new TestNG();
            //runner.setUseDefaultListeners(false);
            List<String> suitList = new ArrayList<String>();
            suitList.add(resources.getAbsolutePath() + "/testing.xml");
            runner.setTestSuites(suitList);
            
            /**
             * Run through TestNG
             */
            //runner.run();
            
            /**
             * Manual run
             */
            RobotTest robotTest = new RobotTest();
            robotTest.handlerMethod("S01", "T01");
            robotTest.test();
            
            
            java.nio.file.Files.walkFileTree(Paths.get(resources.getAbsolutePath()), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(file.toString().endsWith(".xml")) {
                        java.nio.file.Files.delete(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
            
            File xsltReportDir = new File(home.getAbsolutePath() + "/xlst-report/index.html");
            if(!xsltReportDir.getParentFile().exists()) {
                xsltReportDir.getParentFile().mkdirs();
            }
            TransformerFactory tf = TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", null);
            Transformer t = tf.newTransformer(new StreamSource(new File(resources.getAbsolutePath() + "/testng-results.xsl")));
            t.setParameter("testNgXslt.outputDir", xsltReportDir.getParentFile().getAbsolutePath());
            t.setParameter("testNgXslt.showRuntimeTotals", "true");
            t.setParameter("testNGXslt.sortTestCaseLinks", "true");
            t.setParameter("testNgXslt.testDetailsFilter", "FAIL,SKIP,PASS,BY_CLASS");
            t.transform(new StreamSource(new File(home.getAbsolutePath() + "/test-output/testng-results.xml")), new StreamResult(xsltReportDir));
        }
        catch(Exception e) {
            logger.error("Error while input parsing", e);
        }
    }
}
