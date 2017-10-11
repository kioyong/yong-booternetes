package com.yong.security.controller;

import com.yong.security.model.AuthenticationBean;
import com.yong.security.model.ResponseBean;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by LiangYong on 2017/10/1.
 */
@RestController
@CrossOrigin
@Slf4j
public class IndexController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizationServerTokenServices authorizationServerTokenServices;
    @Autowired
    private ClientDetailsService clientDetailsService;

    private DefaultOAuth2RequestFactory requestFactory;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @GetMapping("/index")
    public ResponseBean getHelloWorld(){
        return ResponseBean.success("test success!");
    }

    @PostMapping("/login")
    public OAuth2AccessToken getHelloWorldPost(HttpServletRequest request,
                                          HttpServletResponse response,
                                          @RequestBody AuthenticationBean auth){
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword());
//        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
//        Authentication authResult = this.authenticationManager.authenticate(authRequest);
//        log.debug("Authentication success: " + authResult);
        ResourceOwnerPasswordTokenGranter tokenGranter =
                new ResourceOwnerPasswordTokenGranter(
                        authenticationManager,
                        authorizationServerTokenServices,
                        clientDetailsService,
                        requestFactory
                );
        ClientDetails yong = clientDetailsService.loadClientByClientId("yong");
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("username",auth.getUsername());
        requestParameters.put("password",auth.getPassword());
        Collection<String> scope = new ArrayList<>();
        scope.add("read");
        scope.add("write");
        TokenRequest tokenRequest = new TokenRequest(requestParameters,"yong",scope,"password");
//        tokenGranter.getOAuth2Authentication(yong,tokenRequest);
        OAuth2AccessToken token = tokenGranter.grant("password", tokenRequest);
        return token;
    }

}
