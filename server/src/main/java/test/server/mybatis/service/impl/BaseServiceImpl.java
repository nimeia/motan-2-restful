package test.server.mybatis.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import test.server.mybatis.service.IBaseService;

public class BaseServiceImpl<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T> implements IBaseService<T> {
}
