package com.yong.security.controller;

import com.yong.security.model.ResponseVo;
import org.springframework.web.bind.annotation.*;

/**
 * @author  LiangYong
 * @createdDate 2017/10/1.
 */
@RestController
@CrossOrigin
public class IndexController {

    /**
     * 无任何权限校验接口，测试用 -> localhost:8081/index
     * **/
    @GetMapping("/index")
    public ResponseVo getHelloWorld(){
        return ResponseVo.success("test success!");
    }

}
