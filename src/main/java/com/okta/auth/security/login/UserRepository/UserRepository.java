package com.okta.auth.security.login.UserRepository;

import com.okta.auth.security.login.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


@org.springframework.stereotype.Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>{

    UserEntity findUserEntityByMobile(String mobile);

    boolean existsUserEntityByMobile(String mobile);
}
