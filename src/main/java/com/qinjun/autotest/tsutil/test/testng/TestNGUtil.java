/*
 * Copyright (c) 2018., Qin Jun, All right reserved
 */

package com.qinjun.autotest.tsutil.test.testng;

import com.opencsv.CSVReader;
import com.qinjun.autotest.tsutil.string.StringUtil;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

public class TestNGUtil {
    private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static Object[][] getDataProviderFromCSV(String path) {
        String[][] data=null;
        try {
            CSVReader csvReader = new CSVReader(new FileReader(path));
            List<String[]> csvContent = csvReader.readAll();
            int x = csvContent.size();
            int y = csvContent.get(0).length;
            data = new String[x][y];
            for (int i=0;i<x;i++) {
                for (int j=0;j<y;j++) {
                    data[i][j] = csvContent.get(i)[j];
                }
            }
            csvReader.close();
        } catch(Exception e) {
            logger.error("Get exception when read csv:"+e);
        }
        return data;
    }


    public Object[][] getDataProviderFromExcel(String path, String sheetName) {
        Object[][] data = null;
        try {
            InputStream fs = new FileInputStream(path);
            Workbook workbook = WorkbookFactory.create(fs);
            Sheet sheet = workbook.getSheet(sheetName);
            data = readSheet(sheet);
        } catch(Exception e) {
            logger.error("Get exception when read excel:"+e);
        }
        return data;
    }

    private Object[][] readSheet(Sheet sheet) {
        Object[][] data=null;
        int rowNum = sheet.getLastRowNum();
        data = new Object[rowNum][];

        for (int i=0;i<rowNum;i++) {
            Row row = sheet.getRow(i);
            int colNum = ((Row) row).getLastCellNum();
            data[i] = new Object[colNum];
            for (int j=0;j<colNum;j++) {
                Cell cell = row.getCell(j);
                String value = getValue(cell);
                data[i][j] = value;
            }
        }
        return data;
    }

    private String getValue(Cell cell) {
        String value="";
        final DecimalFormat df = new DecimalFormat("#");
        switch(cell.getCellTypeEnum()) {
            case STRING:
                value = (cell==null ? "": cell.getStringCellValue());
                break;
            case NUMERIC:
                Double dvalue = (cell==null?0:cell.getNumericCellValue());
                value = String.valueOf(dvalue);
                if (value.matches("\\d+.[0]*")) {
                    int endIndex = value.indexOf(".");
                    value = value.substring(0,endIndex);
                }
                if (value.matches("^((-?\\d+.?\\d*)[Ee]{1}(\\d+))$")) {
                    value = df.format(dvalue);
                }
                break;
            case BOOLEAN:
                Boolean bool = (cell==null? false:cell.getBooleanCellValue());
                value=bool.toString();
                break;
            case FORMULA:
                value=(cell==null?"":cell.getCellFormula());
                break;
            case ERROR:
                value =(cell==null?"":Byte.toString(cell.getErrorCellValue()));
                break;
            case BLANK:
                value="";
                break;
        }
        return value;
    }
}
