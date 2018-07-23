package com.qinjun.autotest.tsutil.db.pool;

import com.alibaba.druid.pool.DruidDataSource;
import com.qinjun.autotest.tsutil.exception.ExceptionUtil;
import com.qinjun.autotest.tsutil.exception.TSUtilException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DBPool {
    private static Logger logger = LoggerFactory.getLogger(DBPool.class);
    private DruidDataSource dataSource;
    private Connection conn;

    public DBPool(String url, String user, String pass) throws TSUtilException {
        try {
            dataSource = new DruidDataSource();
            dataSource.setUrl(url);
            dataSource.setUsername(user);
            dataSource.setPassword(pass);
            dataSource.setInitialSize(1);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(10);
            dataSource.setPoolPreparedStatements(false);
            getConnecction();
        }
        catch (Exception e) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
    }

    private synchronized void getConnecction() throws TSUtilException {
        if (conn==null) {
            synchronized (DBPool.class) {
                try {
                    conn = dataSource.getConnection();
                }
                catch (Exception e) {
                    String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(e);
                    logger.error("Get exception:"+exceptionStackTrace);
                    throw new TSUtilException(exceptionStackTrace);
                }
            }
        }
    }

    public synchronized DruidDataSource getDataSource() {return dataSource;}

    public List<Map<String,Object>> runQuery(String sql) throws TSUtilException {
        List<Map<String,Object>> result = null;
        try {
            QueryRunner runner = new QueryRunner(dataSource);
            result = runner.query(sql, new MapListHandler());
        } catch (SQLException sqle) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(sqle);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return result;
    }

    public List<Map<String,Object>> runQueryQuietly(String sql) throws TSUtilException {
        List<Map<String,Object>> result = null;
        try {
            QueryRunner runner = new QueryRunner(dataSource);
            result = runner.query(sql, new MapListHandler());
        } catch (SQLException sqle) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(sqle);
            logger.error("Get exception:"+exceptionStackTrace);
        }
        return result;
    }


    public List<Object[]> runQueryWithArray(String sql) throws TSUtilException {
        List<Object[]> result = null;
        try {
            QueryRunner runner = new QueryRunner(dataSource);
            result = runner.query(sql, new ArrayListHandler());
        } catch (SQLException sqle) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(sqle);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return result;
    }

    public List<Object[]> runQueryWithArrayQuietly(String sql) throws TSUtilException {
        List<Object[]> result = null;
        try {
            QueryRunner runner = new QueryRunner(dataSource);
            result = runner.query(sql, new ArrayListHandler());
        } catch (SQLException sqle) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(sqle);
            logger.error("Get exception:"+exceptionStackTrace);
        }
        return result;
    }

    public int runUpdate(String sql) throws TSUtilException {
        int result = 0;
        try {
            QueryRunner runner = new QueryRunner(dataSource);
            result = runner.update(sql);
        } catch (SQLException sqle) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(sqle);
            logger.error("Get exception:"+exceptionStackTrace);
            throw new TSUtilException(exceptionStackTrace);
        }
        return result;
    }


    public int runUpdateQuietly(String sql) throws TSUtilException {
        int result = 0;
        try {
            QueryRunner runner = new QueryRunner(dataSource);
            result = runner.update(sql);
        } catch (SQLException sqle) {
            String exceptionStackTrace = ExceptionUtil.getExceptionStackTrace(sqle);
            logger.error("Get exception:"+exceptionStackTrace);
        }
        return result;
    }
 }
