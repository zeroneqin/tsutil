/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.string;

import com.qinjun.autotest.tsutil.sleep.SleepUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
    private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static String escapeStr(String str) {
        String escapedStr = StringUtils.replace(str, "\\", "\\\\");
        escapedStr = StringUtils.replace(escapedStr, "\"", "\\\"");
        return escapedStr;
    }
}
