package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.common.R;
import com.domain.User;

public interface UserService extends IService<User> {

    R login(User user);

    R logout();

    R getUserInfo();
}
