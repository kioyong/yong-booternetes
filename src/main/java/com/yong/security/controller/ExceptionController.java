package com.yong.security.controller;

import com.google.common.base.Throwables;
import com.yong.security.model.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author LiangYong
 * @createdDate 2017/10/14.
 */
@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseVo handleArgumentException(Exception ex) {
        log.error("handle exception {}", ex);
        return ResponseVo.error(Throwables.getRootCause(ex).getMessage(), null);
    }
}

