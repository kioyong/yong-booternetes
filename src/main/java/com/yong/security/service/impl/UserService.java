package com.yong.security.service.impl;

import com.yong.security.model.User;
import com.yong.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * Created by LiangYong on 2017/10/1.
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TokenEndpoint tokenEndpoint;

    /**
     * 实现UserDetailsService 的接口，为Spring Security 提供校验方法
     **/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("start load User By Username");
        Optional<User> userEntityOptional = userRepository.findById(username);
        if (userEntityOptional.isPresent()) {
            return userEntityOptional.get();
        } else {
            throw new UsernameNotFoundException("User not exists!");
        }
    }

    /**
     * @Param User
     **/
    public User registerUser(User user) {
        user.init();
        user.setEncryptSessionKey(new BCryptPasswordEncoder().encode(user.getSessionKey()));
        return userRepository.insert(user);
    }

    public Optional<User> findUserByUsername(String username) {
        checkArgument(!username.isEmpty(), "username can't be null");
        return userRepository.findById(username);

    }

    public User updateUserInfo(User user, String openid){
        Optional<User> optional = userRepository.findById(openid);
        if(optional.isPresent()){
            User existsUser = optional.get();
            existsUser.updateInfo(user);
            return userRepository.save(existsUser);
        }else{
            throw new UsernameNotFoundException("User not exists!");
        }

    }

    public void updateOrCreateWxUser(String openid, String sessionKey) {
        Optional<User> userOptional = userRepository.findById(openid.toLowerCase());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setSessionKey(sessionKey);
            user.setEncryptSessionKey(new BCryptPasswordEncoder().encode(sessionKey));
            userRepository.save(user);
        } else {
            User user = new User();
            user.setOpenid(openid);
            user.setSessionKey(sessionKey);
            user.setEncryptSessionKey(new BCryptPasswordEncoder().encode(sessionKey));
            user.init();
            userRepository.save(user);
        }
    }

    public ResponseEntity<OAuth2AccessToken> getJWTToken(String username, String password) throws HttpRequestMethodNotSupportedException {
        PreAuthenticatedAuthenticationToken authenticationToken =
            new PreAuthenticatedAuthenticationToken("yong", "passw0rd");
        authenticationToken.setAuthenticated(true);
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "password");
        parameters.put("username", username);
        parameters.put("password", password);
        return tokenEndpoint.postAccessToken(authenticationToken, parameters);

    }
}
