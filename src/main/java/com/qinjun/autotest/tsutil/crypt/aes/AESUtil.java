/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.crypt.aes;

import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class AESUtil {
    private static final Logger logger = LoggerFactory.getLogger(AESUtil.class);

    public static byte[] encrypt(byte[] keyBytes, byte[] data, byte[] iv) throws TSUtilException {
        byte[] resultBytes=null;
        try {
            Key key = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            resultBytes = cipher.doFinal(data);
            return resultBytes;
        }
        catch(Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
    }


    public static byte[] decrypt(byte[] keyBytes, byte[] data, byte[] iv) throws TSUtilException {
        byte[] resultBytes=null;
        try {
            Key key = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            resultBytes = cipher.doFinal(data);
            return resultBytes;
        }
        catch(Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
    }



}
