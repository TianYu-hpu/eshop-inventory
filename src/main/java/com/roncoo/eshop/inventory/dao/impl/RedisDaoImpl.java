package com.roncoo.eshop.inventory.dao.impl;

import com.roncoo.eshop.inventory.dao.RedisDao;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

@Component
public class RedisDaoImpl implements RedisDao {

    @Resource
    private JedisCluster jedisCluster;

    @Override
    public void set(String key, String value) {
        jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }
}
