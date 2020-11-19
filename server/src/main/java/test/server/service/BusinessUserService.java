package test.server.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * @return
     */
    @Transactional(readOnly = false)
    public List<User> mybatisSave() {
        // 请注意，mybatis 与 jpa 的映射对象不一样
        // 详见 https://mybatis.plus/guide/annotation.html#tableid
        User user = new User();
        user.setName("huang");

        Ordert ordert = new Ordert();
        ordert.setName("my order 1");
//        ordert.setUser(user);

        // 单表保存例子
        // userMapper.insert(user);
        // 也可以使用mybatis 生成的service ，建议使用service。
        userService.save(user);
        // 注意保存后getId 才能有值
        ordert.setUserId(user.getId());
        ordertMapper.insert(ordert);

        //级联保存例子
        User demoUser = new User();
        demoUser.setName("demo2");
        Ordert ordert1 = new Ordert();
        ordert1.setName("order_name1");
        Ordert ordert2 = new Ordert();
        ordert2.setName("order_name2");
        List<Ordert> orderts = new ArrayList<>();
        orderts.add(ordert1);
        orderts.add(ordert2);
        demoUser.setOrdertList(orderts);

        // 级联保存，其实就是把所有sql 写到xml ,不建议使用
        // 这种情况 字表的id 可能不会自动生成，
        ordert1.setId(System.currentTimeMillis() +1);
        ordert2.setId(System.currentTimeMillis() +2);
        userMapper.insertAll(demoUser);

        return null;
    }

    /**
     * mybatis 查询演示，例子中使用 mybatis plus
     * @return
     */
    public List<User> mybatisQuery() {

        // 例子中直接演示使用mapper查询，改造过程中请使用mybatis 生成的service,service 提供更多的方法
        // 例子中使用和mybatis 一对多，多对一的关联，请注意 映射对象 @TableName 中 resultMap = "userResultMap"
        // userResultMap 对应于 UserMapper.xml 中的定义
        // 注意输出时user 与 order 会出现循环引用报错，输出时要转成vo

        // 或者可以用mapper查询 ，mapper 等同于 jap 的repos
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("name", "hello");

        // 带分页的查询
        Page<User> page = new Page<>(0, 2);
        Page<User> userPage = userMapper.selectPage(page, wrapper);

        //按条件查询
        List<User> users = userMapper.selectList(wrapper);

        // 按id 查询
        User user = userMapper.selectByUserId(1L);
        Ordert ordert = ordertMapper.selectById(2L);

        // native sql 分页例子
        // 注意观察 xml 中使用了resultmap ,如果不定义，关联对象不会自动查询
        List<User> userList = userMapper.selectByName("demo");
        IPage<User> userIPage = userMapper.selectByName(page,"hello");

        return null;
    }
}
