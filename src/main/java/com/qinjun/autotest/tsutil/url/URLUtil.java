/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.url;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class URLUtil {
    private final static Logger logger = LoggerFactory.getLogger(URLUtil.class);

    public static String genUrlWithQueryParam(String url, HashMap<String,String> queryParamMap) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?");

        for(String queryParamName : queryParamMap.keySet()) {
            String queryParamValue = queryParamMap.get(queryParamName);
            sb.append(queryParamName);
            sb.append("=");
            sb.append(queryParamValue);
            sb.append("&");
        }

        sb.replace(sb.length()-1,sb.length()-1,"'");
        String resultUrl = sb.toString();
        return resultUrl;
    }

}
