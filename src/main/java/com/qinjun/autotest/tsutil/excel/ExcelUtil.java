/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.excel;

import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import com.qinjun.autotest.tsutil.http.HttpUtil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

public class ExcelUtil {
    private final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static Workbook getWorkBook(String path) throws TSUtilException {
        FileInputStream fileInputStream = null;
        Workbook workbook = null;
        try {
            fileInputStream = new FileInputStream(path);
            workbook = WorkbookFactory.create(fileInputStream);
        } catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        } finally {
            try {
                fileInputStream.close();
            } catch (Exception e) {
                logger.warn("Get exception when close the file:" + e);
            }
        }
        return workbook;
    }

    public static Sheet getSheet(String path, String sheetName) throws TSUtilException {
        Sheet sheet = null;
        try {
            Workbook workbook = getWorkBook(path);
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return sheet;
    }


    public static Sheet getSheet(Workbook workbook, String sheetName) throws TSUtilException {
        Sheet sheet = null;
        try {
            sheet = workbook.getSheet(sheetName);
        } catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return sheet;
    }

    public static Cell getCell(String path, String sheetName, int cellRowNum, int cellColumnNum) throws TSUtilException {
        Cell cell = null;
        try {
            Sheet sheet = getSheet(path, sheetName);
            Row row = sheet.getRow(cellRowNum);
            cell = row.getCell(cellColumnNum);
        } catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return cell;
    }


    public static Cell getCell(Sheet sheet, int cellRowNum, int cellColumnNum) throws TSUtilException {
        Cell cell = null;
        try {
            Row row = sheet.getRow(cellRowNum);
            cell = row.getCell(cellColumnNum);
        } catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return cell;
    }

    public static String getCellValue(String path, String sheetName, int cellRowNum, int cellColumnNum) throws TSUtilException {
        String result = null;
        try {
            Cell cell = getCell(path, sheetName, cellRowNum, cellColumnNum);
            DecimalFormat decimalFormat = new DecimalFormat("#");
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case STRING:
                    result = (cell == null ? "" : cell.getStringCellValue());
                    break;
                case NUMERIC:
                    Double tmpDouble = (cell == null ? 0 : cell.getNumericCellValue());
                    result = String.valueOf(tmpDouble);
                    if (result.matches("\\d+.[0]*]")) {
                        int index = result.indexOf(".");
                        result = result.substring(0, index);
                    }
                    if (result.matches("^((-?\\d+.?\\d*)[Ee]{1}(\\d+))$")) {
                        result = decimalFormat.format(tmpDouble);
                    }
                    break;
                case BOOLEAN:
                    Boolean tmpBoolean = (cell == null ? false : cell.getBooleanCellValue());
                    result = String.valueOf(tmpBoolean);
                    break;
                case FORMULA:
                    result = (cell == null ? "" : cell.getCellFormula());
                    break;
                case ERROR:
                    result = (cell == null ? "" : Byte.toString(cell.getErrorCellValue()));
                    break;
                case BLANK:
                    result = "";
                    break;
            }
        } catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return result;
    }


    public static String getCellValue(Cell cell) throws TSUtilException {
        String result = null;
        try {
            DecimalFormat decimalFormat = new DecimalFormat("#");
            CellType cellType = cell.getCellTypeEnum();
            switch (cellType) {
                case STRING:
                    result = (cell == null ? "" : cell.getStringCellValue());
                    break;
                case NUMERIC:
                    Double tmp = (cell == null ? 0 : cell.getNumericCellValue());
                    result = String.valueOf(tmp);
                    if (result.matches("\\d+.[0]*]")) {
                        int index = result.indexOf(".");
                        result = result.substring(0, index);
                    }
                    if (result.matches("^((-?\\d+.?\\d*)[Ee]{1}(\\d+))$")) {
                        result = decimalFormat.format(tmp);
                    }
                    break;
                case BOOLEAN:
                    Boolean tmpBoolean = (cell == null ? false : cell.getBooleanCellValue());
                    result = String.valueOf(tmpBoolean);
                    break;
                case FORMULA:
                    result = (cell == null ? "" : cell.getCellFormula());
                    break;
                case ERROR:
                    result = (cell == null ? "" : Byte.toString(cell.getErrorCellValue()));
                    break;
                case BLANK:
                    result = "";
                    break;
            }
        } catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return result;
    }


    public static String[][] getSheetContent(String path, String sheetName) throws TSUtilException {
        String[][] content = null;
        try {
            Sheet sheet = getSheet(path, sheetName);
            int lastRowNum = sheet.getLastRowNum();
            Row row = sheet.getRow(0);
            int lastCellNum = row.getLastCellNum();

            content = new String[lastRowNum + 1][lastCellNum];
            for (int i = 0; i <= lastRowNum; i++) {
                for (int j = 0; j < lastCellNum; j++) {
                    Cell cell = getCell(sheet, i, j);
                    String value = getCellValue(cell);
                    content[i][j] = value;
                }
            }
        } catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return content;
    }

    public static void saveWorkBook(Workbook workbook,String path) throws TSUtilException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            workbook.write(fileOutputStream);
            fileOutputStream.close();
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
    }

    public static void saveCellValue(String path, String sheetName,int rowNum, int columnNum, String value) throws TSUtilException {
        try {
            Workbook workbook = getWorkBook(path);
            Sheet sheet = getSheet(workbook,sheetName);
            Cell cell = getCell(sheet,rowNum,columnNum);
            cell.setCellValue(value);
            saveWorkBook(workbook,path);
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:" + exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
    }



}
