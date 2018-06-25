package com.bao.miaosha.redis;

import com.bao.miaosha.util.ProperUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * 不采用SPringboot 获取jedis方案
 *
 */
public class RedisPool {

    private static JedisPool jedisPool;
    private static String host = ProperUtil.getProperty("redis.host");
    private static int port = Integer.parseInt(ProperUtil.getProperty("redis.port"));
    private static int timeout = Integer.parseInt(ProperUtil.getProperty("redis.timeout"));
    private static int poolMaxTotal = Integer.parseInt(ProperUtil.getProperty("redis.poolMaxTotal"));
    private static int poolMaxIdle = Integer.parseInt(ProperUtil.getProperty("redis.poolMaxIdle"));
    private static int poolMinIdle = Integer.parseInt(ProperUtil.getProperty("redis.poolMaxIdle"));
    private static String poolMaxWait =  ProperUtil.getProperty("redis.poolMaxWait");

    public static void initJedisPool(){
        JedisPoolConfig jedisConfig = new JedisPoolConfig();
        jedisConfig.setMaxTotal(poolMaxTotal);
        jedisConfig.setMaxIdle(poolMaxIdle);
        jedisConfig.setMinIdle(poolMinIdle);
        jedisConfig.setBlockWhenExhausted(true);
        jedisPool = new JedisPool(jedisConfig, RedisPool.host, RedisPool.port, RedisPool.timeout * 1000);
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

    static {
        RedisPool.initJedisPool();
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        System.out.println(jedis.get("MiaoshaUserKey:token8516e9a9793e4aa48cca3195518399eb"));
        jedis.set("bao", "dada");
        System.out.println(jedis.get("bao"));

    }
}
