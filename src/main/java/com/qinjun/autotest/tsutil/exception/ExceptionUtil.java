/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.exception;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ExceptionUtil {
    public static String getExceptionStackTrace(Throwable throwable) {
        String[] stackTraces = ExceptionUtils.getRootCauseStackTrace(throwable);
        return StringUtils.join(stackTraces);
    }
}
