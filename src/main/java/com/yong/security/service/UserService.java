package com.yong.security.service;

import com.yong.security.model.User;
import reactor.core.publisher.Mono;

/**
 * Created by LiangYong on 2017/10/1.
 */
public interface UserService {
    Mono<User> registerUser(User user);
}
