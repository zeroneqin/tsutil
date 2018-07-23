/*
 * Copyright (c) 2018., Qin Jun, All right reserved
 */

package com.qinjun.autotest.tsutil.html;

import org.apache.commons.lang3.StringUtils;

public class HtmlUtil {

    public static String htmlEscape(String input) {
        if(StringUtils.isEmpty(input)){
            return null;
        }
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");
        input = input.replaceAll(" ", "&nbsp;");
        input = input.replaceAll("'", "&#39;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("\n", "<br/>");
        return input;
    }
}
