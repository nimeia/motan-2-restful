package test.server.repo;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.server.mybatis.mapper.UserMapper;
import test.server.mybatis.service.IUserService;

@Component
public class UserJpaToMybatisRepos {

    @Autowired
    UserMapper userMapper;

    @Autowired
    IUserService iUserService;



}
