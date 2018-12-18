package com.roncoo.eshop.inventory.service.impl;

import com.alibaba.fastjson.JSON;
import com.roncoo.eshop.inventory.dao.RedisDao;
import com.roncoo.eshop.inventory.domain.User;
import com.roncoo.eshop.inventory.mapper.UserMapper;
import com.roncoo.eshop.inventory.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisDao redisDao;

    @Override
    public User findUserInfo() {
        return userMapper.selectByPrimaryKey(1);
    }

    @Override
    public User getCachedUserInfo() {
        User user = JSON.parseObject(redisDao.get("cache"), User.class);
        if(user == null) {
            user = userMapper.selectByPrimaryKey(2);
            log.info("查询数据库");
            redisDao.set("cache", JSON.toJSONString(user));
        }
        return user;
    }
}
