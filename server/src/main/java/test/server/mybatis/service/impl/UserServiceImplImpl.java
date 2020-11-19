package test.server.mybatis.service.impl;

import test.server.mybatis.entity.User;
import test.server.mybatis.mapper.UserMapper;
import test.server.mybatis.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hgq@sinosoft
 * @since 2020-11-11
 */
@Service
public class UserServiceImplImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {

}
