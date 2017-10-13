package com.yong.security;

import com.yong.security.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import static com.yong.security.Oauth2ServerConfig.AuthorizationServerConfiguration.tokenStore;

/**
 * Created by LiangYong on 2017/10/8.
 */
@Configuration
public class Oauth2ServerConfig {
    private static final String RESOURCE_ID = "yong";

    @Configuration
    @EnableWebSecurity
    protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(this.userDetailsService);
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {

//            http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
//
//            http
//                    .requestMatcher(new AntPathRequestMatcher("/oauth/**"))
//                    .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//            http.authorizeRequests()
//                    .antMatchers("/rest/**").anonymous()
//                    .and()
//                    .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//            http.authorizeRequests().anyRequest().permitAll();

        }

        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

    @Configuration
    @Order(-1)
    public class CorsConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/token","/rest/**")
                    .and()
                    .authorizeRequests().anyRequest().permitAll()
                    .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) {
//            resources.resourceId("resource_id").stateless(false);
////            resources.tokenStore(tokenStore());
//        }
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()
                .antMatchers(HttpMethod.GET,
                    "/",
                    "/*.html",
                    "/favicon.ico",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js").permitAll()
                .antMatchers("/oauth/**","/user/register", "/login","/index","/error","/").permitAll()
                .antMatchers("/swagger-ui.*","/swagger-resources/**","/webjars/**","/v2/api-docs").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

//            @Autowired
        private ClientDetailsService clientDetailsService;

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            return new JwtAccessTokenConverter();
        }
        @Bean
        public TokenStore tokenStore() {
            return new JwtTokenStore(accessTokenConverter());
        }
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

            final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
            defaultTokenServices.setTokenStore(tokenStore());
            defaultTokenServices.setTokenEnhancer(accessTokenConverter());
            defaultTokenServices.setClientDetailsService(clientDetailsService);
            resources.resourceId(RESOURCE_ID).tokenServices(defaultTokenServices);
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
//                    .tokenStore(tokenStore())
//                    .tokenEnhancer(tokenEnhancerChain())
                    .accessTokenConverter(accessTokenConverter())
                    .authenticationManager(authenticationManager)
//                    .userDetailsService(userDetailsService)  //已经在WebSecurityConfig注入userDetailsService,此处不用注入,共用同一个userDetailsService
//                    .setClientDetailsService(inMemoryClientDetailsService) //在下面配置为inMemory() clients,配置一个使用,此处不做clientDetailService实现,同userDetailService实现方法类似
                    ;
        }
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient("yong")
                    .secret("passw0rd")
                    .authorities("ROLE_TRUSTED_CLIENT")
                    .accessTokenValiditySeconds(3600)
                    .authorizedGrantTypes("password")
                    //,"client_credentials",  "refresh_token", "implicit", "authorization_code" 其他Grant
                    .scopes("read", "write")
                    .autoApprove("read", "write");
//            authorizedGrantTypes =
        }

//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer oauthServer)
//                throws Exception {
//            oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
////            oauthServer.tokenKeyAccess("isAnonymous() || hasAuthority('ROLE_TRUSTED_CLIENT')").checkTokenAccess(
////                    "hasAuthority('ROLE_TRUSTED_CLIENT')");
//        }
        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            return new JwtAccessTokenConverter();
        }
//        private JwtAccessTokenConverter tokenEnhancerChain() {
//            JwtAccessTokenConverter tokenEnhancerChain = new JwtAccessTokenConverter();
//            tokenEnhancerChain.setAccessTokenConverter(jwtAccessTokenConverter());
//            return tokenEnhancerChain;
//        }
//
//        @Bean
//        public TokenStore tokenStore() {
//            return new InMemoryTokenStore();
//        }
//        @Bean
//        public  JwtTokenStore tokenStore() {
//            return new JwtTokenStore(jwtAccessTokenConverter());
//        }
//
//        @Bean
//        public JwtAccessTokenConverter jwtAccessTokenConverter() {
//            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
////            DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
////            defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
////            DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
////            jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
//            jwtAccessTokenConverter.setSigningKey("yong_secret1");
//            return jwtAccessTokenConverter;
//        }
    }
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
//        public CustomUserApprovalHandler userApprovalHandler() throws Exception {
//            CustomUserApprovalHandler handler = new CustomUserApprovalHandler();
//            handler.setApprovalStore(approvalStore());
//            handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
//            handler.setClientDetailsService(clientDetailsService);
//            handler.setUseApprovalStore(true);
//            return handler;
//        }
//    }

}
