package test.server.mybatis.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import test.server.mybatis.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

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

    List<User> selectByName(String name);

    IPage<User> selectByName(Page<User> page, String name);
}
