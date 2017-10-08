package com.yong.security;

import com.yong.security.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

/**
 * Created by LiangYong on 2017/10/8.
 */
@Configuration
public class Oauth2ServerConfig {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId("resource_id").stateless(false);
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS).permitAll()
                    .antMatchers("/tokenttd","/oauth/token").permitAll()
                    .anyRequest().authenticated();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;
        @Autowired
        private AuthenticationManager authenticationManager;

        @Value("${security.oauth2.client.client-secret}")
        private String RedirectUri;
        @Value("${security.oauth2.client.client-id}")
        private String client_id;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            tokenEnhancerChain.setTokenEnhancers(
                    Arrays.asList(/*tokenEnhancer(),*/ jwtAccessTokenConverter()));
            endpoints.tokenStore(tokenStore())
                    .tokenEnhancer(tokenEnhancerChain)
                    .accessTokenConverter(jwtAccessTokenConverter())
                    .authenticationManager(authenticationManager);

        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient(client_id.trim())
                    .authorities("ROLE_TRUSTED_CLIENT")
                    .secret(RedirectUri.trim())
                    .accessTokenValiditySeconds(3600)
                    .authorizedGrantTypes("client_credentials", "password", "refresh_token", "implicit", "authorization_code")
                    .scopes("read", "write")
                    .autoApprove("read", "write");
        }
        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer)
                throws Exception {
            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        }
        @Bean
        public TokenStore tokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey("yong_secret");
            return jwtAccessTokenConverter;
        }
//        @Bean
//        @Primary
//        public DefaultTokenServices tokenServices() {
//            DefaultTokenServices tokenServices = new DefaultTokenServices();
//            tokenServices.setTokenStore(tokenStore());
//            tokenServices.setSupportRefreshToken(true);
//            return tokenServices;
//        }
//        @Bean
//        public TokenEnhancer tokenEnhancer() {
//            return new CustomTokenEnhancer();
//        }

    }

//
//    protected static class Stuff {
//
//        @Autowired
//        private ClientDetailsService clientDetailsService;
//
//        @Autowired
//        private TokenStore tokenStore;
//
//        @Bean
//        public ApprovalStore approvalStore() throws Exception {
//            TokenApprovalStore store = new TokenApprovalStore();
//            store.setTokenStore(tokenStore);
//            return store;
//        }
//
//        @Bean
//        @Lazy
//        @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
//        public ApprovalStoreUserApprovalHandler userApprovalHandler() throws Exception {
//            ApprovalStoreUserApprovalHandler handler = new ApprovalStoreUserApprovalHandler();
//            handler.setApprovalStore(approvalStore());
//            handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//            handler.setClientDetailsService(clientDetailsService);
//            return handler;
//        }
//    }
}
