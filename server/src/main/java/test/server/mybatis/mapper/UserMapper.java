package test.server.mybatis.mapper;

import test.server.mybatis.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hgq@sinosoft
 * @since 2020-11-11
 */
public interface UserMapper extends BaseMapper<User> {


    User selectByUserId(long l);
}
