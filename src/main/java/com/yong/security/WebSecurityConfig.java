package com.yong.security;

import com.yong.security.service.impl.UserDetailServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;

/**
 * Created by LiangYong on 2017/10/1.
 */


//@Component
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService);
//        auth.inMemoryAuthentication().withUser("admin").password("admin").roles("user");
    }

    /**
     * 校验规则
     * **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/index","/","/index/**","/register","/swagger**").permitAll()
				.anyRequest().authenticated()
//                .anyRequest().permitAll()
                .and().formLogin()/*.loginPage("/login")*/.defaultSuccessUrl("/user/", true).permitAll()
                .and().logout().permitAll()
                .and().csrf().disable();
    }

}
