package com.yong.security.controller;

import com.yong.security.model.AuthenticationBean;
import com.yong.security.model.ResponseBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

/**
 * Created by LiangYong on 2017/10/1.
 */
@RestController
@CrossOrigin
@Slf4j
public class IndexController {

//    private ConsumerTokenServices tokenServices;
//    private CustomUserApprovalHandler userApprovalHandler;
//        private JsonParser objectMapper = JsonParserFactory.create();
//    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
//    @Autowired
//    private TokenStore tokenStore;
//    private AuthorizationEndpoint auth;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private AuthorizationServerTokenServices authorizationServerTokenServices;
//    @Autowired
//    private ClientDetailsService clientDetailsService;
//    private ResourceServerTokenServices resourceServerTokenServices;
//    private DefaultOAuth2RequestFactory requestFactory;

    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    @GetMapping("/index")
    public ResponseBean getHelloWorld(){
        return ResponseBean.success("test success!");
    }

    @PostMapping("/login")
    public OAuth2AccessToken getHelloWorldPost(HttpServletRequest request,
                                          HttpServletResponse response,
                                          @RequestBody AuthenticationBean auth){
//        Map<String, String> map = new HashMap<>();
//        map.put("client_id","yong");
//        map.put("grant_type","password");
//        ClientDetails clientDetails = inMemoryClientDetailsService.loadClientByClientId("yong");
//        TokenRequest tokenRequest = factory.createTokenRequest(map, clientDetails);
//        OAuth2Request oAuth2Request = factory.createOAuth2Request(clientDetails, tokenRequest);
//        oAuth2Request.
        return null;
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword());
//        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
//        Authentication authResult = this.authenticationManager.authenticate(authRequest);
//        log.debug("Authentication success: " + authResult);
//        ResourceOwnerPasswordTokenGranter tokenGranter =
//                new ResourceOwnerPasswordTokenGranter(
//                        authenticationManager,
//                        authorizationServerTokenServices,
//                        clientDetailsService,
//                        requestFactory
//                );
//        ClientDetails yong = clientDetailsService.loadClientByClientId("yong");
//        Map<String, String> requestParameters = new HashMap<>();
//        requestParameters.put("username",auth.getUsername());
//        requestParameters.put("password",auth.getPassword());
//        Collection<String> scope = new ArrayList<>();
//        scope.add("read");
//        scope.add("write");
//        TokenRequest tokenRequest = new TokenRequest(requestParameters,"yong",scope,"password");
//        tokenGranter.getOAuth2Authentication(yong,tokenRequest);
//        OAuth2AccessToken token = tokenGranter.grant("password", tokenRequest);
//        return token;
    }


    @RequestMapping("/oauth/clients/")
    @ResponseBody
    public Collection<OAuth2AccessToken> listTokensForUser() throws Exception {
//        return enhance(tokenStore.findTokensByClientId("yong"));
//        auth
        return null;
    }
    private void checkResourceOwner(String user, Principal principal) {
        if (principal instanceof OAuth2Authentication) {
            OAuth2Authentication authentication = (OAuth2Authentication) principal;
            if (!authentication.isClientOnly() && !user.equals(principal.getName())) {
                throw new AccessDeniedException(String.format("User '%s' cannot obtain tokens for user '%s'",
                        principal.getName(), user));
            }
        }
    }
//    private Collection<OAuth2AccessToken> enhance(Collection<OAuth2AccessToken> tokens) {
//        Collection<OAuth2AccessToken> result = new ArrayList<OAuth2AccessToken>();
//        for (OAuth2AccessToken prototype : tokens) {
//            DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(prototype);
//            OAuth2Authentication authentication = tokenStore.readAuthentication(token);
//            if (authentication == null) {
//                continue;
//            }
////            String clientId = authentication.getOAuth2Request().getClientId();
//            String clientId = "yong";
//            if (clientId != null) {
//                Map<String, Object> map = new HashMap<String, Object>(token.getAdditionalInformation());
//                map.put("client_id", clientId);
//                token.setAdditionalInformation(map);
//                result.add(token);
//            }
//        }
//        return result;
//    }
//    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        DefaultOAuth2AccessToken result = new DefaultOAuth2AccessToken(accessToken);
//        Map<String, Object> info = new LinkedHashMap(accessToken.getAdditionalInformation());
//        String tokenId = result.getValue();
//        if(!info.containsKey("jti")) {
//            info.put("jti", tokenId);
//        } else {
//            tokenId = (String)info.get("jti");
//        }
//
//        result.setAdditionalInformation(info);
//        result.setValue(this.encode(result, authentication));
//        OAuth2RefreshToken refreshToken = result.getRefreshToken();
//        if(refreshToken != null) {
//            DefaultOAuth2AccessToken encodedRefreshToken = new DefaultOAuth2AccessToken(accessToken);
//            encodedRefreshToken.setValue(refreshToken.getValue());
//            encodedRefreshToken.setExpiration((Date)null);
//
//            try {
//                Map<String, Object> claims = this.objectMapper.parseMap(JwtHelper.decode(refreshToken.getValue()).getClaims());
//                if(claims.containsKey("jti")) {
//                    encodedRefreshToken.setValue(claims.get("jti").toString());
//                }
//            } catch (IllegalArgumentException var11) {
//                ;
//            }
//
//            Map<String, Object> refreshTokenInfo = new LinkedHashMap(accessToken.getAdditionalInformation());
//            refreshTokenInfo.put("jti", encodedRefreshToken.getValue());
//            refreshTokenInfo.put("ati", tokenId);
//            encodedRefreshToken.setAdditionalInformation(refreshTokenInfo);
//            DefaultOAuth2RefreshToken token = new DefaultOAuth2RefreshToken(this.encode(encodedRefreshToken, authentication));
//            if(refreshToken instanceof ExpiringOAuth2RefreshToken) {
//                Date expiration = ((ExpiringOAuth2RefreshToken)refreshToken).getExpiration();
//                encodedRefreshToken.setExpiration(expiration);
//                token = new DefaultExpiringOAuth2RefreshToken(this.encode(encodedRefreshToken, authentication), expiration);
//            }
//
//            result.setRefreshToken((OAuth2RefreshToken)token);
//        }
//
//        return result;
//    }
//
//    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
//        String content;
//        try {
//            content = this.objectMapper.formatMap(this.tokenConverter.convertAccessToken(accessToken, authentication));
//        } catch (Exception var5) {
//            throw new IllegalStateException("Cannot convert access token to JSON", var5);
//        }
//
//        String token = JwtHelper.encode(content, new MacSigner("yong_secret")).getEncoded();
//        return token;
//    }

}
