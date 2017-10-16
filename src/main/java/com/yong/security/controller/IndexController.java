package com.yong.security.controller;

import com.yong.security.model.ResponseVo;
import com.yong.security.model.UserEntity;
import com.yong.security.repository.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.function.Function;

/**
 * @author  LiangYong
 * @createdDate 2017/10/1.
 */
@RestController
@CrossOrigin
@AllArgsConstructor
public class IndexController {

    /**
     * 无任何权限校验接口，测试用 -> localhost:8081/index
     * 此controller所有接口都是测试用
     * **/

    private final UserDao userDao;

    @GetMapping("/index")
    public ResponseVo getHelloWorld(){
        return ResponseVo.success("test success!");
    }

//    @GetMapping("/test/{username}")
//    public Mono getUser(@PathVariable String username){
//        userDao.findById(username)
//            .timeout(Duration.ofMillis(800))//设置timeout 测试
//            .flatMap(IndexController::getDetail);
////            .map(ResponseVo::success);
//        return null;
//    }

//    public static Mono getDetail(Object userEntity){
//        //TODO do something
//        return Mono.just(userEntity);
//    }

}
