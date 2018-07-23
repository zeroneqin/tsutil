/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.sleep;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SleepUtil {
    private final static Logger logger = LoggerFactory.getLogger(SleepUtil.class);

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException ie) {
            logger.warn("Sleep get interrupted");
        }
    }
}
