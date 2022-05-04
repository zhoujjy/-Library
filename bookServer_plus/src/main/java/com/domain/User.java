package com.domain;
import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer userId;       //用户id
    private String userName;      //用户名称
    private String userPassword;  //用户密码
    private String userEmail;     //用户邮箱（用户账号）
    private String userRole;      //用户角色
    private String userStatus;    //用户状态

}
