package com.yong.security.repository;

import com.yong.security.model.UserEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by LiangYong on 2017/10/1.
 */
@Repository
public interface UserDao extends ReactiveMongoRepository<UserEntity,String> {
}