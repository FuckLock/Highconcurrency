package com.bao.miaosha.redis;


import com.bao.miaosha.util.DateUtil;
import com.bao.miaosha.util.ObjectMapperUtil;
import com.sun.org.apache.xerces.internal.parsers.CachingParserPool;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.text.SimpleDateFormat;

@Service
public class RedisService {

    /**
     * 不可以定义redis属性,在高并发下,jedis变量变成共享变量会出现线程安全性问题
     */
//    private Jedis jedis;
    private static Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    private JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key,  Class<T> clazz) {
        Jedis jedis= null;
        try {
            jedis =  jedisPool.getResource();
            String realKey = prefix.setPrefix() + key;
            String str = jedis.get(realKey);
            return ObjectMapperUtil.stringToBean(str, clazz);
        }finally {
            returnToPool(jedis);
        }
    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        boolean result;
        Jedis jedis= null;
        try {
            jedis =  jedisPool.getResource();
            String str = ObjectMapperUtil.beanToString(value);
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(str)){
                return false;
            }
            String realKey  = prefix.setPrefix() + key;
            jedis.set(realKey, str);
            result = true;
        }finally {
            returnToPool(jedis);
        }
        return result;
    }

    public <T> boolean setEx(KeyPrefix prefix, String key, T value, int exTime){
        boolean result;
        Jedis jedis= null;
        try {
            jedis =  jedisPool.getResource();
            String str = ObjectMapperUtil.beanToString(value);
            if (StringUtils.isEmpty(key) || StringUtils.isEmpty(str)){
                return false;
            }
            String realKey  = prefix.setPrefix() + key;
            jedis.setex(realKey, exTime, str);
            result = true;
        } finally {
            returnToPool(jedis);
        }
        return result;
    }

    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis= null;
        try {
            jedis =  jedisPool.getResource();
            String realKey  = prefix.setPrefix() + key;
            return jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     * */
    public <T> Long incr(KeyPrefix prefix, T key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            String realKey  = prefix.setPrefix() + key;
            return jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     * */
    public <T> Long decr(KeyPrefix prefix, T key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.setPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    public boolean delete(KeyPrefix prefix, String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.setPrefix() + key;
            Long result = jedis.del(realKey);
            return result > 0;
        }finally {
            returnToPool(jedis);
        }
    }

    private void returnToPool(Jedis jedis) {
        if (jedis != null){
            jedis.close();
        }
    }
}
