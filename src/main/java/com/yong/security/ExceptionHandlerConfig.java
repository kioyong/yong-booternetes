package com.yong.security;

import com.google.common.base.Throwables;
import com.yong.security.model.ResponseVo;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by LiangYong on 2017/10/14.
 */
@RestControllerAdvice
public class ExceptionHandlerConfig {


    @ExceptionHandler(Exception.class)
    public ResponseVo handleArgumentException(Exception ex) {
        List<Map<String, String>> list = Arrays.stream(Throwables.getRootCause(ex).getStackTrace())
                .filter(t ->
                        t.getClassName().startsWith("com.yong") &&
                        t.getLineNumber() > 0 &&
                        !t.getFileName().equals("CorsFilter.java")
                )
                .map(t -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("methodName", t.getMethodName());
                    map.put("fileName", t.getFileName());
                    map.put("lineNumber", t.getLineNumber() + "");
                    return map;
                }).collect(Collectors.toList());
        return ResponseVo.error(Throwables.getRootCause(ex).getMessage(),list);
    }
}
