package com.controller;

import com.common.BaseContext;
import com.common.Code;
import com.common.R;
import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user
     * @return R
     */
    @PostMapping("/login")
    public R login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * 用户退出
     * @return R
     */
    @PostMapping("/logout")
    public R logout(){
        return userService.logout();
    }

    /**
     * 获得用户信息
     * @return
     */
    @GetMapping
    public R getUserInfo(){
        return userService.getUserInfo();
    }
}
