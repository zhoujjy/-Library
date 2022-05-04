package com.interceptor;

import com.common.BaseContext;
import com.common.Code;
import com.domain.User;
import com.exception.BusinessException;
import com.utils.JwtUtil;

import com.utils.RedisUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtils redisUtils;
    // 创建一个路径匹配器,支持通配符,spring提供的
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //跨域时会发送两个请求，先发送预检请求options，通过后再发送正确的请求，因此要先放行options
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }

        //获得请求url
        String url = request.getRequestURI();
        String[] urls = new String[]{ //需要权限控制的url
                "/book/confirmReturn/**",
                "/book",
        };


        // 1. 获取token
        String token = request.getHeader("token");

        // 2.判断token是否为空
        if(ObjectUtils.isEmpty(token)){
            throw new BusinessException(Code.ERROR,"未登录，请先登录");
        }

        //3.验证token，当验证失败时，抛出异常
        Claims tokenInfo = null;
        try {
            tokenInfo = JwtUtil.getTokenClaim(token);
        }catch (Exception e){
            throw new BusinessException(Code.ERROR,"登录状态异常，请重新登录",e);
        }

        //4. 获取用户id
        String userId = tokenInfo.get("userId").toString();

        //5. 根据用户id查询redis
        User user = redisUtils.getObject("user:"+userId,User.class);
        if(ObjectUtils.isEmpty(user)){
            throw new BusinessException(Code.ERROR,"登录状态异常，请重新登录");
        }

        //6.判断是否需要检查
        boolean check = check(urls,url);
        if (check && user.getUserRole().equals("USER")) {
            throw new BusinessException(Code.ERROR,"您没有权限访问该资源");
        }

        //7 将用户redis key存入线程中，方便后面调用
        BaseContext.setCurrentId("user:"+userId);
        return true;
    }

    /**
     * 匹配路径
     * @param urls
     * @param url
     * @return
     */
    public boolean check(String[] urls,String url){
        for (String u : urls) {
            if (PATH_MATCHER.match(u, url)) {
                return true;
            }
        }
        return false;
    }
}
