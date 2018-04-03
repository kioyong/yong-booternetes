package com.yong.security.service;

import com.yong.security.model.UserEntity;

/**
 * Created by LiangYong on 2017/10/1.
 */
public interface UserService {
    UserEntity registerUser(UserEntity user);
    UserEntity findUserByUsername(String username);

}
