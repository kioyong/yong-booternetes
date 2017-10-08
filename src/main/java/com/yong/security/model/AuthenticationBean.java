package com.yong.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by LiangYong on 2017/10/6.
 */
@Data
@AllArgsConstructor
public class AuthenticationBean {
    private String username;
    private String password;
}