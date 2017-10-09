package com.yong.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yong.security.model.AuthenticationBean;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LiangYong on 2017/10/5.
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

// not ues it yet
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
            ObjectMapper mapper = new ObjectMapper();
            AuthenticationBean auth;
            try (InputStream is = request.getInputStream()){
                 auth = mapper.readValue(is,AuthenticationBean.class);
            }catch (IOException e) {
                e.printStackTrace();
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken("","");
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
            String username = auth.getUsername().trim();
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, auth.getPassword());
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
    }

}
