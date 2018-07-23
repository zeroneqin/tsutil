/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.crypt.sha2;

import com.qinjun.autotest.tsutil.compress.gzip.GZipUtil;
import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHAUtil {
    private static final Logger logger = LoggerFactory.getLogger(GZipUtil.class);

    public static String createHash(String plainText, String charsetName) throws TSUtilException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = messageDigest.digest(plainText.getBytes(charsetName));
            return new String(hashedBytes, charsetName);
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
    }

}