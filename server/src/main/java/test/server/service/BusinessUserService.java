package test.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.server.entity.OrderEntity;
import test.server.entity.UserEntity;
import test.server.mybatis.entity.Ordert;
import test.server.mybatis.entity.User;
import test.server.mybatis.mapper.OrdertMapper;
import test.server.mybatis.mapper.UserMapper;
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
    IUserService userService;

    /**
     * jpa 保存 功能样例
     */
    @Transactional(readOnly = false)
    public void japSave() {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("demo1");

        // _1 只能在例子中使用，生成不可以这样命名
        OrderEntity orderEntity_1 = new OrderEntity();
        orderEntity_1.setName("order1");
        OrderEntity orderEntity_2 = new OrderEntity();
        orderEntity_2.setName("order2");

        List<OrderEntity> orderEntities = new ArrayList<>();
        orderEntities.add(orderEntity_1);
        orderEntities.add(orderEntity_2);
        userEntity.setOrderEntityList(orderEntities);

        userRepos.save(userEntity);
    }

    /**
     * jpa
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<UserEntity> jpaQuery() {

        // example 查询
        // 可以使用 mybatis 的 QueryWrapper 替换
        UserEntity userEntity = new UserEntity();
        userEntity.setName("demo1");
        Example<UserEntity> userEntityExample = Example.of(userEntity);

        // jpa 基础的查询功能，具体查询 spring data jpa 的查询功能 ，该功能只能手动适配对应的功能
        List<UserEntity> all = userRepos.findAll(userEntityExample);
        List<UserEntity> demoUsers = userRepos.findAllByName("demo1");

        // @query 注解查询 hql ，可以使用mybatis 的自定义查询替换，不做特殊说明
        List<UserEntity> userEntities = userRepos.queryByUserName("demo1");
        userEntities = userRepos.queryByUserNameUseNativeSql("demo1");

        // @Query 注解查询带分页
        Pageable pageable = PageRequest.of(0, 2);
        org.springframework.data.domain.Page<UserEntity> userEntityPage = userRepos.queryPageByUserName("demo1", pageable);

        userEntityPage = userRepos.queryPageByUserNameUseNativeSql("demo1", pageable);
        return all;
    }

    public List<User> findAllUserPo() {
        //你可以调用自动生成的service 去查询
//        List<User>  users = userService.list();
//        return users;

        //或者可以用mapper查询 ，mapper 等同于 jap 的repos
        QueryWrapper wrapper = new QueryWrapper();

        wrapper.eq("name", "hello");
//        wrapper.order
//
//        User user1 = new User();
//        user1.setName("huang");
//        userMapper.insert(user1);

//        Long id=1328991782443573249L;

        Page<User> page = new Page<>(0, 10);
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
