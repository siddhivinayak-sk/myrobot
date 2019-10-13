/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myrobot.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSheetConditionalFormatting;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.myrobot.model.RoboSuit;
import com.myrobot.model.RoboTask;
import com.myrobot.model.RoboTest;

/**
 * This is a class containing utility methods for excel reading and writing
 * @author sandeepkumar
 */
public class ExcelUtil {
    private static Logger logger = Logger.getLogger(ExcelUtil.class);
    
    /**
     * This method takes input directory of excel sheets, process and return
     * list of RoboSuits for input excel sheets.
     * @param inputDir Input parameter
     * @return Returns List&lt;RoboSuit&gt;
     * @throws Exception Throws runtime exception
     */
    public static List<RoboSuit> scanSuits(String inputDir) throws Exception {
        List<RoboSuit> roboSuitList = new ArrayList<RoboSuit>();
        logger.info(inputDir);
        Files.walkFileTree(Paths.get(inputDir), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if(!path.toString().contains("~") && (path.toString().endsWith(".xls") || path.toString().endsWith(".XLS") || path.toString().endsWith(".xlsx") || path.toString().endsWith(".XLSX"))) {
                    try {
                        List<RoboSuit> tempSuitList = readPath(path);
                        if(null != tempSuitList && tempSuitList.size() > 0) {
                            roboSuitList.addAll(tempSuitList);
                        }
                    }
                    catch(Exception e) {
                        logger.error("Error in Reading Path: ", e);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return roboSuitList;
    }
    
    /**
     * This method takes path of excel sheet and return list of RoboSuits
     * @param path Input parameter
     * @return Returns List&lt;RoboSuit&gt;
     * @throws Exception Throws runtime exception
     */
    public static List<RoboSuit> readPath(Path path) throws Exception {
        List<RoboSuit> result = new ArrayList<RoboSuit>();
        try {
            Workbook workbook = null;
            if(path.toString().endsWith(".xls") || path.toString().endsWith(".XLS")) {
                workbook = new HSSFWorkbook(Files.newInputStream(path, StandardOpenOption.READ));
            }
            else if(path.toString().endsWith(".xlsx") || path.toString().endsWith(".XLSX")) {
                workbook = new XSSFWorkbook(Files.newInputStream(path, StandardOpenOption.READ));
            }
            if(null == workbook) {
                throw new Exception("Invalid Excel");
            }
            Sheet suitSheet = workbook.getSheet("Suits");
            if(null == suitSheet) {
                throw new Exception("Invalid Excel: No 'Suits' worksheet");
            }
            for(int i = 1; i <= suitSheet.getLastRowNum();i++) {
                Row row = suitSheet.getRow(i);
                Cell name = row.getCell(0);
                Cell description = row.getCell(1);
                Cell parallel = row.getCell(2);
                Cell isRun = row.getCell(3);
                if(null != name && !"".equals(getStringValueFromCell(name))) {
                    RoboSuit rs = new RoboSuit();
                    rs.setId((long)i);
                    rs.setName(getStringValueFromCell(name));
                    rs.setDescription(getStringValueFromCell(description));
                    rs.setParallel((null != parallel && getStringValueFromCell(parallel).equalsIgnoreCase("y"))?true:false);
                    rs.setIsRun((null != isRun && getStringValueFromCell(isRun).equalsIgnoreCase("y"))?true:false);
                    result.add(rs);
                }
            }
            Sheet testsSheet = workbook.getSheet("Tests");
            List<RoboTest> roboTestList = new ArrayList<RoboTest>();
            if(null == testsSheet) {
                throw new Exception("Invalid Excel: No 'Tests' worksheet");
            }
            for(int i = 1; i <= testsSheet.getLastRowNum();i++) {
                Row row = testsSheet.getRow(i);
                Cell name = row.getCell(0);
                Cell suitName = row.getCell(1);
                Cell stepSheetName = row.getCell(2);
                Cell className = row.getCell(3);
                Cell isRun = row.getCell(4);
                if(
                        null != suitName && null != suitName.getStringCellValue()
                        && null != stepSheetName && null != stepSheetName.getStringCellValue()
                        && null != className && null != className.getStringCellValue()
                        ) {
                    RoboTest rt = new RoboTest();
                    rt.setId((long)i);
                    rt.setName(getStringValueFromCell(name));
                    rt.setSuitName(getStringValueFromCell(suitName));
                    rt.setStepSheetName(getStringValueFromCell(stepSheetName));
                    rt.setClassName(getStringValueFromCell(className));
                    rt.setIsRun((null != isRun && getStringValueFromCell(isRun).equalsIgnoreCase("y"))?true:false);
                    roboTestList.add(rt);
                }
            }
            if(roboTestList.size() < 1) {
                throw new Exception("No test case found");
            }
            
            for(RoboTest rt:roboTestList) {
                Sheet stepSheet = workbook.getSheet(rt.getStepSheetName());
                if(null == stepSheet) {
                    continue;
                }
                List<RoboTask> taskList = new ArrayList<RoboTask>();
                for(int i = 1; i <= stepSheet.getLastRowNum(); i++) {
                    Row row = stepSheet.getRow(i);
                    Cell description = row.getCell(0);
                    Cell action = row.getCell(1);
                    Cell selectBy = row.getCell(2);
                    Cell element = row.getCell(3);
                    Cell data = row.getCell(4);
                    Cell waitTime = row.getCell(5);
                    Cell condition = row.getCell(6);
                    Cell waitSelectBy = row.getCell(7);
                    Cell waitElementId = row.getCell(8);
                    Cell repeat = row.getCell(9);
                    if(null != action && null != action.getStringCellValue()) {
                        RoboTask rT = new RoboTask();
                        rT.setDescription(getStringValueFromCell(description));
                        rT.setAction(getStringValueFromCell(action));
                        rT.setSelectBy(getStringValueFromCell(selectBy));
                        rT.setElementId(getStringValueFromCell(element));
                        rT.setData(getStringValueFromCell(data));
                        rT.setWaitTime(getStringValueFromCell(waitTime));
                        rT.setExpectedCondition(getStringValueFromCell(condition));
                        rT.setWaitElementId(getStringValueFromCell(waitElementId));
                        rT.setWaitSelectBy(getStringValueFromCell(waitSelectBy));
                        rT.setRepeatTimes(getStringValueFromCell(repeat));
                        taskList.add(rT);
                    }
                }
                if(null != taskList && taskList.size() > 0) {
                    rt.setTaskList(taskList);
                }
            }
            
            for(RoboSuit rs:result) {
                if(null != rs.getName() && !rs.getName().equals("")) {
                    for(RoboTest rt:roboTestList) {
                        if(rs.getName().equalsIgnoreCase(rt.getSuitName())) {
                            if(null == rs.getTestList()) {
                                rs.setTestList(new ArrayList<RoboTest>());
                            }
                            rs.getTestList().add(rt);
                        }
                    }
                }
            }
        }
        catch(Exception e) {
            logger.error("Error: Reading file " + path.toString(), e);
        }
        return result;
    }
    
    /**
     * This method takes RoboTest as parameter and write the complete RoboTest
     * in output directory as excel.
     * @param test Input parameter
     */
    public static void writeResult(RoboTest test) {
        try {
            if(null == test) {
                throw new Exception("Invalid Test");
            }
            if(null == test.getName() || null == test.getSuitName() || null == test.getStepSheetName() || null == test.getTaskList() || test.getTaskList().size() <= 0) {
                throw new Exception("Test Data is not complete");
            }
            String outputDir = System.getProperty("output.excel.dir");
            if(!new File(outputDir).exists()) {
                new File(outputDir).mkdirs();
            }
            String outputFile = outputDir + "/" + test.getSuitName() + "_" + test.getName() + ".xls";
            Workbook workbook = new HSSFWorkbook();
            
            Sheet testSheet = workbook.createSheet("Test");
            Row testRow = testSheet.createRow(0);
            Cell cId = testRow.createCell(0);
            cId.setCellValue(test.getId());
            Cell cSuitName = testRow.createCell(1);
            cSuitName.setCellValue(test.getSuitName());
            Cell cName = testRow.createCell(2);
            cName.setCellValue(test.getName());
            Cell cStepSheetName = testRow.createCell(3);
            cStepSheetName.setCellValue(test.getStepSheetName());
            Cell cClassName = testRow.createCell(4);
            cClassName.setCellValue(test.getClassName());
            Cell cIsRun = testRow.createCell(5);
            cIsRun.setCellValue((null == test.getIsRun() || !test.getIsRun())?"n":"y");
            Cell cResult = testRow.createCell(6);
            cResult.setCellValue(test.getResult());

            Sheet stepsSheet = workbook.createSheet(test.getStepSheetName());
            int rowIndex = 0;
            for(RoboTask task:test.getTaskList()) {
                Row row = stepsSheet.createRow(rowIndex);
                rowIndex++;
                Cell rcId = row.createCell(0);
                rcId.setCellValue((null != task.getId())?task.getId():0);
                Cell rcDescription = row.createCell(1);
                rcDescription.setCellValue((null != task.getDescription())?task.getDescription():"");
                Cell rcAction = row.createCell(2);
                rcAction.setCellValue((null != task.getAction())?task.getAction():"");
                Cell rcSelectBy = row.createCell(3);
                rcSelectBy.setCellValue((null != task.getSelectBy())?task.getSelectBy():"");
                Cell rcElementId = row.createCell(4);
                rcElementId.setCellValue((null != task.getElementId())?task.getElementId():"");
                Cell rcData = row.createCell(5);
                rcData.setCellValue((null != task.getData())?task.getData():"");
                Cell rcWaitTime = row.createCell(6);
                rcWaitTime.setCellValue((null != task.getWaitTime())?task.getWaitTime():"");
                Cell rcExpectedCondition = row.createCell(7);
                rcExpectedCondition.setCellValue((null != task.getExpectedCondition())?task.getExpectedCondition():"");
                Cell rcWaitSelectBy = row.createCell(8);
                rcWaitSelectBy.setCellValue((null != task.getWaitSelectBy())?task.getWaitSelectBy():"");
                Cell rcWaitElementId = row.createCell(9);
                rcWaitElementId.setCellValue((null != task.getWaitElementId())?task.getWaitElementId():"");
                Cell rcRepeatTimes = row.createCell(10);
                rcRepeatTimes.setCellValue((null != task.getRepeatTimes())?task.getRepeatTimes():"");
                Cell rcResult = row.createCell(11);
                rcResult.setCellValue((null != task.getResult())?task.getResult():"");
            }
            workbook.write(new FileOutputStream(outputFile));
         }
        catch(Exception e) {
            logger.error("Error: Write file ", e);
        }
    }
    
    /**
     * This takes cell object of POI and return the cell values as string
     * @param cell Input parameter
     * @return Returns String type value
     */
    public static String getStringValueFromCell(Cell cell) {
        String result = "";
        if(null ==  cell) {
            return result;
        }
        cell.setCellType(Cell.CELL_TYPE_STRING);
        result = cell.getStringCellValue();
        return result;
    }
}
