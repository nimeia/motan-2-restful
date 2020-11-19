package test.server.mybatis.service;

import test.server.entity.UserEntity;
import test.server.mybatis.entity.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hgq@sinosoft
 * @since 2020-11-11
 */
public interface IUserService extends IBaseService<User> {

    List<UserEntity> findAllByName(String name);
}
