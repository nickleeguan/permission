package com.icode.service.impl;

import com.google.common.base.Joiner;
import com.icode.beans.CacheKeyConstants;
import com.icode.service.interfaces.SysCacheService;
import com.icode.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

@Service
public class SysCacheServiceImpl implements SysCacheService {

    private static final Logger logger = LoggerFactory.getLogger(SysCacheServiceImpl.class);

    @Resource(name = "redisPool")
    private RedisPool redisPool;

    @Override
    public void saveCache(String value, int timeout, CacheKeyConstants prefix) {
        saveCache(value, timeout, prefix, null);
    }

    @Override
    public void saveCache(String value, int timeout, CacheKeyConstants prefix, String... keys) {
        if (value == null){
            return;
        }
        ShardedJedis shardedJedis = null;
        try{
            String cacheKey = generateCacheKey(prefix, keys);
            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey, timeout, value);
        }catch (Exception e){
            logger.error("缓存存储异常!");
        }finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    public String getFromCache(CacheKeyConstants prefix, String... keys){
        ShardedJedis shardedJedis = null;
        String cacheKey = generateCacheKey(prefix, keys);
        try {
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            return value;
        }catch (Exception e){
            logger.error("get from cache exception,prefix:{}, keys:{}", prefix.name(), JsonMapper.obj2String(e));
            return null;
        }finally {
            if (shardedJedis != null){
                redisPool.safeClose(shardedJedis);
            }
        }
    }

    private String generateCacheKey(CacheKeyConstants prefix, String... keys){
        String key = prefix.name();
        if (keys != null && keys.length > 0){
            key += "_" + Joiner.on("_").join(keys);
        }

        return key;
    }
}
