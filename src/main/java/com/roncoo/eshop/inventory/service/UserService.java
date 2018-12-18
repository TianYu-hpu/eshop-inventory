package com.roncoo.eshop.inventory.service;

import com.roncoo.eshop.inventory.domain.User;

public interface UserService {

    User findUserInfo();

    User getCachedUserInfo();

}
