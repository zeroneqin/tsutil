/* Copyright(c), Qin Jun, All right serverd. */
package com.qinjun.autotest.tsutil.redis;

import com.qinjun.autotest.tsutil.json.JSONUtil;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

public class RedisUtil {
    private final static Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    public static Jedis buildClient(String host,int port,String pass) {
        Jedis jedis = new Jedis(host,port);
        if (!StringUtils.isEmpty(pass)) {
            jedis.auth(pass);
        }
        return jedis;
    }

    public static void set(Jedis jedis,String key, String value) {
        jedis.set(key,value);
    }

    public String get(Jedis jedis,String key) {
        return jedis.get(key);
    }



}
