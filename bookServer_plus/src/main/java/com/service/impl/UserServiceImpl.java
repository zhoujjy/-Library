package com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.common.BaseContext;
import com.common.Code;
import com.common.R;
import com.dao.UserDao;
import com.domain.User;
import com.service.UserService;
import com.utils.JwtUtil;
import com.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 用户登录
     * @param user
     * @return R
     */
    @Override
    public R login(User user) {
        //1. 获取密码，并进行加密
        String password = user.getUserPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());


        //2. 根据用户名查询用户
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserEmail, user.getUserEmail());
        User queryUser = userDao.selectOne(wrapper);

        //3. 判断用户是否存在
        if(ObjectUtils.isEmpty(queryUser)){
            return new R(Code.ERROR, "用户不存在");
        }

        //4.查询到了，判断密码是否正确
        if(!queryUser.getUserPassword().equals(password)) {
            return new R(Code.ERROR, "密码错误");
        }

        //5. 查看状态 0：正常 1：禁用
        if(queryUser.getUserStatus() == "1") {
            return new R(Code.ERROR, "账号已被禁用");
        }

        //6. 登录成功
        //6.1 生成token
        String token = JwtUtil.createToken(queryUser.getUserId().toString());
        //6.2 将用户数据存入redis 并设置30分钟过期时间
        redisUtils.setObject("user:" + queryUser.getUserId(), queryUser, 30);
        //6.3 返回token
        Map<String,String> map = new HashMap<String,String>();
        map.put("token", token);
        return new R(Code.SUCCESS,map ,"登录成功" );
    }


    /**
     * 用户退出
     * @return
     */
    @Override
    public R logout() {
        //1. 获取redis userKey
        String redisUserKey = BaseContext.getCurrentId();
        //2. 删除redis中的userKey
        if(!ObjectUtils.isEmpty(redisUserKey)){
            redisUtils.removeValue(redisUserKey);
        }
        return new R(Code.SUCCESS, "退出成功");
    }

    @Override
    public R getUserInfo() {
        //1. 获取redis userKey
        String redisUserKey = BaseContext.getCurrentId();
        //2. 获取redis中的信息
        User user =  redisUtils.getObject(redisUserKey, User.class);
        return new R(Code.SUCCESS, user, "获取用户角色成功");
    }
}
