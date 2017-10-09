package com.yong.security;

import com.yong.security.service.impl.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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

//import static com.yong.security.Oauth2ServerConfig.AuthorizationServerConfiguration.tokenStore;

/**
 * Created by LiangYong on 2017/10/8.
 */
@Configuration
public class Oauth2ServerConfig {

    @Configuration
    @EnableWebSecurity
    @EnableGlobalAuthentication
    protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(this.userDetailsService);
        }
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.authorizeRequests()
//                    .antMatchers("/oauth/**","/user/register", "/login","/index","/error","/").permitAll()
//                    .antMatchers("/swagger-ui.*","/swagger-resources/**","/webjars/**","/v2/api-docs").permitAll()
//                    .anyRequest().authenticated();
//        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }

    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId("resource_id").stateless(false);
            resources.tokenStore(tokenStore());
        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/oauth/**","/user/register", "/login","/index","/error","/").permitAll()
                    .antMatchers("/swagger-ui.*","/swagger-resources/**","/webjars/**","/v2/api-docs").permitAll()
                .anyRequest().authenticated();
            //"**.css","**.js","**.png","**.ico

        }

        @Bean
        public JwtTokenStore tokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey("yong_secret");
            return jwtAccessTokenConverter;
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
        private String secret;
        @Value("${security.oauth2.client.client-id}")
        private String client_id;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.tokenStore(tokenStore())
                    .tokenEnhancer(tokenEnhancerChain())
                    .accessTokenConverter(jwtAccessTokenConverter())
                    .authenticationManager(authenticationManager);
//                    .userDetailsService(userDetailsService)  //已经在WebSecurityConfig注入userDetailsService,此处不用注入,共用同一个userDetailsService
//                    .setClientDetailsService(clientDetailService) //在下面配置为inMemory() clients,配置一个使用,此处不做clientDetailService实现,同userDetailService实现方法类似

        }
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient(client_id.trim())
                    .secret(secret.trim())
                    .authorities("ROLE_TRUSTED_CLIENT")
                    .accessTokenValiditySeconds(3600)
                    .authorizedGrantTypes("password")
                    //,"client_credentials",  "refresh_token", "implicit", "authorization_code" 其他Grant
                    .scopes("read", "write")
                    .autoApprove("read", "write");
//            authorizedGrantTypes =
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer)
                throws Exception {
//            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
            oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')").checkTokenAccess(
                    "hasAuthority('ROLE_TRUSTED_CLIENT')");
        }

        private TokenEnhancer tokenEnhancerChain() {
            TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
            tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter()));
            return tokenEnhancerChain;
        }
        @Bean
        public JwtTokenStore tokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey("yong_secret");
            return jwtAccessTokenConverter;
        }
    }

}
