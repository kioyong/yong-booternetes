package com.yong.security.controller;

import com.yong.security.model.AuthenticationBean;
import com.yong.security.model.ResponseBean;
import org.springframework.web.bind.annotation.*;

/**
 * Created by LiangYong on 2017/10/1.
 */
@RestController
@CrossOrigin
public class IndexController {

    @GetMapping("/index")
    public ResponseBean getHelloWorld(){
        return ResponseBean.success("test success!");
    }

    @PostMapping("/postTest")
    public ResponseBean getHelloWorldPost(@RequestBody AuthenticationBean authenticationBean){
        return ResponseBean.success("test success!",authenticationBean);
    }

    @GetMapping("/loginSuccess")
    public ResponseBean loginSuccess(){
        return ResponseBean.success("login Success!");
    }

    @GetMapping("/loginFail")
    public ResponseBean getError(){return ResponseBean.error("Authenication error");}
}
