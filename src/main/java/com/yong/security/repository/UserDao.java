package com.yong.security.repository;

import com.yong.security.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author  LiangYong
 * @createdDate 2017/10/1.
 */
@Repository
public interface UserDao extends MongoRepository<UserEntity,String> {
}