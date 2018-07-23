/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.compress.gzip;

import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtil {
    private static final Logger logger = LoggerFactory.getLogger(GZipUtil.class);

    public static byte[] compress(byte[] bytes) throws TSUtilException {
        if (bytes==null || bytes.length==0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(bytes);
            gzipOutputStream.close();
        }
        catch (IOException ioe) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(ioe);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] unCompress(byte[] bytes) throws  TSUtilException {
        if (bytes==null || bytes.length==0) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            byte[] buffer = new byte[256];
            int n;
            while((n=gzipInputStream.read(buffer))>=0) {
                byteArrayOutputStream.write(buffer,0,n);
            }
        }
        catch (IOException ioe) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(ioe);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return byteArrayOutputStream.toByteArray();
    }
}
