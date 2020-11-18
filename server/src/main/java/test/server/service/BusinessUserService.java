package test.server.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import test.server.entity.OrderEntity;
import test.server.entity.UserEntity;
import test.server.mybatis.entity.Ordert;
import test.server.mybatis.entity.User;
import test.server.mybatis.mapper.OrdertMapper;
import test.server.mybatis.mapper.UserMapper;
import test.server.mybatis.service.IOrdertService;
import test.server.mybatis.service.IUserService;
import test.server.repo.UserRepos;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessUserService {

    @Autowired
    UserRepos userRepos;

    @Autowired
    UserMapper userMapper;

    @Autowired
    OrdertMapper ordertMapper;


    @Autowired
//    @Qualifier()
    IUserService userService;

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

    public List<User> findAllUserPo() {
        //你可以调用自动生成的service 去查询
//        List<User>  users = userService.list();
//        return users;

        //或者可以用mapper查询 ，mapper 等同于 jap 的repos
        QueryWrapper wrapper = new QueryWrapper();

        wrapper.eq("name","hello");
//        wrapper.order
//
//        User user1 = new User();
//        user1.setName("huang");
//        userMapper.insert(user1);

//        Long id=1328991782443573249L;

        Page<User> page = new Page<>(0,10);
        Page<User> userPage = userMapper.selectPage(page, wrapper);
        List<User> records = userPage.getRecords();


        List<User> users = userMapper.selectList(wrapper);

        User user = userMapper.selectByUserId(1L);

        Ordert ordert = ordertMapper.selectById(2L);
//        System.out.println(ordert);
        //注意输出时user 与 order 会出现循环引用报错，输出时要转成vo

//        userMapper.
        return null;
    }
}
