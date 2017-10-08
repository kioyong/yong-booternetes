package com.yong.security.service.impl;

import com.yong.security.model.UserBean;
import com.yong.security.repository.UserDao;
import com.yong.security.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


/**
 * Created by LiangYong on 2017/10/1.
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService,UserService {

    private final UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("start load User By Username");
        UserBean user = dao.findById(username).block();
        if (user == null) throw new UsernameNotFoundException("username not found");
        return user;
    }

    @Override
    public Mono<UserBean> registerUser(UserBean user) {
        user.init();
        return dao.insert(user);
    }

    @Override
    public Mono<UserBean> findUserByUsername(String username){
        return dao.findById(username);
    }
}
