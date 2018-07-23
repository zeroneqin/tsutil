/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.csv;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

public class CSVUtil {
    private static final Logger logger = LoggerFactory.getLogger(CSVUtil.class);

    public static String[][] getContent(String csvFile) throws TSUtilException {
        String[][] csvContents = null;

        try {
            CSVReader csvReader = new CSVReader(new FileReader(csvFile));
            List<String[]> content = csvReader.readAll();
            if (content==null || content.size()==0) {
                logger.warn("CSV file is empty");
            }
            else {
                int rowSize = content.size();
                int columnSize = content.get(0).length;
                csvContents = new String[rowSize][columnSize];
                for (int i=0;i<rowSize;i++) {
                    for (int j=0;j<columnSize;j++) {
                        csvContents[i][j] = content.get(i)[j];
                    }
                }
            }

            csvReader.close();
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }

        return csvContents;
    }

    public static void writeContent(String csvFile, String[][] csvContents) throws TSUtilException {
        try {
            CSVWriter csvWriter = new CSVWriter(new FileWriter(csvFile));
            for (String[] row: csvContents) {
                csvWriter.writeNext(row);
            }
            csvWriter.close();
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
    }

}
