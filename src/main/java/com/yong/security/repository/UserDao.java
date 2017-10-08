package com.yong.security.repository;

import com.yong.security.model.UserBean;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by LiangYong on 2017/10/1.
 */
@Repository
public interface UserDao extends ReactiveMongoRepository<UserBean,String> {
}