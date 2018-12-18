package com.roncoo.eshop.inventory.controller;

import com.roncoo.eshop.inventory.domain.User;
import com.roncoo.eshop.inventory.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@RequestMapping(value = "/user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getUserInfo")
    @ResponseBody
    public User getUserInfo() {
        return userService.findUserInfo();
    }

    @GetMapping(value = "/getCachedUserInfo")
    @ResponseBody
    public User getCachedUserInfo() {
        return userService.getCachedUserInfo();
    }

}
