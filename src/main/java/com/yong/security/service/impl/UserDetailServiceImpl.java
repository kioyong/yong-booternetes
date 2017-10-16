package com.yong.security.service.impl;

import com.yong.security.model.UserEntity;
import com.yong.security.repository.UserDao;
import com.yong.security.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * Created by LiangYong on 2017/10/1.
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService,UserService {

    private final UserDao dao;

    /**
     * 实现UserDetailsService 的接口，为Spring Security 提供校验方法
     * **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("start load User By Username");
        UserEntity user = dao.findById(username).block();
        return user;
    }

    @Override
    public Mono<UserEntity> registerUser(UserEntity user) {
        checkArgument(!user.getName().isEmpty(),"username can't be null");
        checkArgument(!user.getPassword().isEmpty(),"password can't be null");
        user.init();
        return dao.insert(user);
    }

    @Override
    public Mono<UserEntity> findUserByUsername(String username){
        checkArgument(!username.isEmpty(),"username can't be null");
        return dao.findById(username);
    }
}
