package com.yong.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by LiangYong on 2017/10/1.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {

    private int code;

    private String message;

    private Object data;

    /**
     * 正常返回
     */
    public static ResponseBean success(Object object) {
        return new ResponseBean(0, "success", object);
    }

    /**
     * 错误返回
     */
    public static ResponseBean error(Exception exception) {
        return new ResponseBean(-1, "error", exception.getMessage());
    }
}