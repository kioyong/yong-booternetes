package com.yong.security.service;

import com.yong.security.model.UserBean;
import reactor.core.publisher.Mono;

/**
 * Created by LiangYong on 2017/10/1.
 */
public interface UserService {
    Mono<UserBean> registerUser(UserBean user);
    Mono<UserBean> findUserByUsername(String username);

}
