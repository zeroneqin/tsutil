/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.crypt.md5;

import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;

public class MD5Util {
    private static final Logger logger = LoggerFactory.getLogger(MD5Util.class);

    public static String getMD5(String str) throws TSUtilException{
        String resultStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            resultStr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return resultStr;
    }
}
