package com.yong.security;

import com.google.common.base.Throwables;
import com.yong.security.model.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author LiangYong
 * @createdDate 2017/10/14.
 */
@RestControllerAdvice
@Slf4j
@ConfigurationProperties
public class ExceptionHandlerConfig {

    @ExceptionHandler(Exception.class)
    public ResponseVo handleArgumentException(Exception ex) {
        log.error("handle exception {}", ex);
        return ResponseVo.error(Throwables.getRootCause(ex).getMessage(), null);
    }
}

