package com.yong.security.service;

import com.yong.security.model.UserEntity;

import java.util.Optional;

/**
 * Created by LiangYong on 2017/10/1.
 */
public interface UserService {
    UserEntity registerUser(UserEntity user);
    Optional<UserEntity> findUserByUsername(String username);

}
