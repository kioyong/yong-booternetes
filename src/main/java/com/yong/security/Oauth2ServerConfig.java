package com.yong.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author LiangYong
 * @createdDate 2017/10/8.
 */
@Configuration
public class Oauth2ServerConfig {

    @Configuration
    @EnableWebSecurity
    protected static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        public void configure(@Autowired AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(this.userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.requestMatchers().antMatchers(HttpMethod.OPTIONS, "/oauth/**")
                .and().authorizeRequests().anyRequest().permitAll()
                .and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            return tokenEnhancerChain();
        }

        private JwtAccessTokenConverter tokenEnhancerChain() {
            JwtAccessTokenConverter tokenEnhancerChain = new JwtAccessTokenConverter();
            tokenEnhancerChain.setAccessTokenConverter(jwtAccessTokenConverter());
            return tokenEnhancerChain;
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
            defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
            DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
            jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
            jwtAccessTokenConverter.setSigningKey("yong_secret1");
            return jwtAccessTokenConverter;
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(this.authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                .withClient("yong")
                .secret("passw0rd")
                .authorities("ROLE_TRUSTED_CLIENT")
                .accessTokenValiditySeconds(3600)
                .authorizedGrantTypes("client_credentials", "password", "refresh_token", "implicit", "authorization_code")
                .scopes("read", "write")
                .autoApprove("read", "write")
            ;
        }
    }

    @Configuration
    @EnableResourceServer
    @EnableGlobalMethodSecurity(prePostEnabled = true)
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        private ClientDetailsService clientDetailsService;

        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public JwtAccessTokenConverter accessTokenConverter() {
            return tokenEnhancerChain();
        }

        private JwtAccessTokenConverter tokenEnhancerChain() {
            JwtAccessTokenConverter tokenEnhancerChain = new JwtAccessTokenConverter();
            tokenEnhancerChain.setAccessTokenConverter(jwtAccessTokenConverter());
            return tokenEnhancerChain;
        }

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            DefaultUserAuthenticationConverter defaultUserAuthenticationConverter = new DefaultUserAuthenticationConverter();
            defaultUserAuthenticationConverter.setUserDetailsService(userDetailsService);
            DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
            jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter);
            jwtAccessTokenConverter.setSigningKey("yong_secret2");
            return jwtAccessTokenConverter;
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
            resources.resourceId("yong").tokenServices(defaultTokenServices);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.css", "/**/*.js", "/**/*.png").permitAll()
                .antMatchers("/user/register", "/index", "/v2/api-docs", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated().and().csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

}
