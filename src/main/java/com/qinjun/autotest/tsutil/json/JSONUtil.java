/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.json;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by qinjun.qj on 2018/2/7.
 */
public class JSONUtil {
    private final static Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public static String minify(String json) {
        String minifiedJson=null;

        String[] jsonLines = json.split(System.lineSeparator());
        for (int i=0;i<jsonLines.length;i++) {
            if (jsonLines[i].matches("\\s*//[^/]*")) {
                jsonLines[i]="";
            }
        }

        StringBuilder minifiedJsonSb= new StringBuilder();
        for(String jsonLine : jsonLines){
            if(!jsonLine.equals("")){
                minifiedJsonSb.append(jsonLine).append(System.lineSeparator());
            }
        }
        minifiedJson = minifiedJsonSb.toString();

        return minifiedJson;
    }

}
