package test.server.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.server.entity.OrderEntity;
import test.server.entity.UserEntity;
import test.server.entity.UserPo;
import test.server.repo.UserMapper;
import test.server.repo.UserRepos;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserService {

    @Autowired
    UserRepos userRepos;

    @Autowired
    UserMapper userMapper;

    public List<UserEntity> findAll() {
        UserEntity user1 = new UserEntity();
        user1.setName("hello");

        UserEntity user2 = new UserEntity();
        user2.setName("world");

        List<UserEntity> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setName("baidu");
        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(orderEntity);
        user1.setOrderEntityList(orderEntities);

        userRepos.saveAll(users);

        return userRepos.findAll();
    }

    public List<UserPo> findAllUserPo() {
        Wrapper wrapper = new QueryWrapper();

        return userMapper.selectList(wrapper);
    }
}
