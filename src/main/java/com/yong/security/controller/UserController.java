package com.yong.security.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.yong.security.model.AuthenticationVo;
import com.yong.security.model.ResponseVo;
import com.yong.security.model.User;
import com.yong.security.service.impl.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;


/**
 * @author yong.a.liang
 * createdDate 2017/10/1.
 */
@Slf4j
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final WxMaService wxService;

    @GetMapping("/login")
    public ResponseEntity<OAuth2AccessToken> mpLogin(@RequestParam(name = "code") String code) throws Exception {
        WxMaJscode2SessionResult session = this.wxService.getUserService().getSessionInfo(code);
        log.info("openid = {}, sessionKey = {}", session.getOpenid(), session.getSessionKey());
        userService.updateOrCreateWxUser(session.getOpenid(), session.getSessionKey());
        return userService.getJWTToken(session.getOpenid(), session.getSessionKey());
    }


    @PostMapping("/info")
    public User updateUserInfo(@RequestBody User user, @AuthenticationPrincipal Principal principal) {
        String openid = principal.getName();
        return userService.updateUserInfo(user, openid);
    }

    /**
     * 注册功能，Body上需要输入用户名和密码，
     * 当前只添加了用户名密码不能为空，且用户名没有被注册过
     * restController Post request 默认的RequestBody也是 application/json格式的
     * TODO 取消注册功能，当前只允许微信小程序登陆
     **/
    @PostMapping(path = "/register",consumes = "application/json")
    public ResponseVo registerUser(@RequestBody AuthenticationVo auth) {
        Optional<User> user = this.userService.findUserByUsername(auth.getUsername());
        checkArgument(!user.isPresent(), "User " + auth.getUsername() + " already exists!");
        User userEntity = this.userService.registerUser(
            User.builder().openid(auth.getUsername()).sessionKey(auth.getPassword()).build()
        );
        return ResponseVo.success(userEntity);
    }

}
