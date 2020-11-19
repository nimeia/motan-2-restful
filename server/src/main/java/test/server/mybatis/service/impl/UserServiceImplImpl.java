package test.server.mybatis.service.impl;

import org.springframework.stereotype.Service;
import test.server.entity.UserEntity;
import test.server.mybatis.entity.User;
import test.server.mybatis.mapper.UserMapper;
import test.server.mybatis.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author hgq@sinosoft
 * @since 2020-11-11
 */
@Service
public class UserServiceImplImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public List<UserEntity> findAllByName(String name) {
        List<User> userList = this.baseMapper.selectByName(name);

        // 关于映射对象有两种处理方法，一种是直接使用jpa的对象，那处理程序就不需要处理对象的转换，只要拉取对应的字对象就可以
        // 另外一种是像下面手动转换
        // 当直接使用jpa 的原对象时，需要调用外需，把对应的外键字段加到对应的类中，其它，最好不好使用代码生成工具，
        // 并手动生成 mapper.jar mapper.xml XXservice.java xxServiceimpl.java 
        List<UserEntity> userEntities = new ArrayList<>();
        for (User user : userList) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(user.getId());
            userEntity.setName(user.getName());
            // 处理其它逻辑

            userEntities.add(userEntity);
        }

        return userEntities;
    }
}
