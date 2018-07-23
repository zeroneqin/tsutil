/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.db.pool;

import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DBPoolFactory {
    private final static Logger logger = LoggerFactory.getLogger(DBPoolFactory.class);

    private static Map<String,DBPool> dbPoolMap = new HashMap<String, DBPool>();
     public static DBPool getInstance(String url, String username, String password) {
         DBPool dbPool = null;
         String key = url+"_"+username;

         if (!dbPoolMap.containsKey(key)) {
             try {
                 dbPool = new DBPool(url, username, password);
             }
             catch (TSUtilException tsue) {
                 String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(tsue);
                 logger.error("Get exception:"+exceptionStackTrace);
             }
             dbPoolMap.put(key, dbPool);
         }
         return dbPool;
     }
}
