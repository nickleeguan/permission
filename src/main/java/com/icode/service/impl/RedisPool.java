package com.icode.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

@Component("redisPool")
public class RedisPool {

    private static final Logger logger = LoggerFactory.getLogger(RedisPool.class);

    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedis instance(){
        return shardedJedisPool.getResource();
    }

    public void safeClose(ShardedJedis shardedJedis){
        try{
            if (shardedJedis != null){
                shardedJedis.close();
            }
        }catch (Exception e){
            logger.error("redis 连接关闭失败");
        }
    }
}
