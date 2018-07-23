
/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.binary;


import com.qinjun.autotest.tsutil.compress.gzip.GZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ByteUtil {
    private static final Logger logger = LoggerFactory.getLogger(GZipUtil.class);
    
    public static String bytesToHexString(byte[] data) {
        if (data == null) return null;
        
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02X ", b));
        }
        
        return sb.toString();
    }

}
