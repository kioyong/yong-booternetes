package com.yong.security.service.impl;

import com.yong.security.model.User;
import com.yong.security.repository.UserDao;
import com.yong.security.service.UserService;
import lombok.AllArgsConstructor;
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
public class UserDetailServiceImpl implements UserDetailsService,UserService {

    private final UserDao dao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = dao.findById(username).block();
        return user;
    }

    @Override
    public Mono<User> registerUser(User user) {
        user.init();
        return dao.insert(user);
    }

    public Mono<User> findUserByUsername(String username){
        return dao.findById(username);
    }
}
