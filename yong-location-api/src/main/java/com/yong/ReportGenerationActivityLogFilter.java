package com.yong;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class ReportGenerationActivityLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getServletPath();
        log.info("path info: {}", path);
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        parameterMap.forEach((k, v) -> {
            log.info("param name : {} , value : {}", k, v);
        });
        filterChain.doFilter(request, response);
    }
}
