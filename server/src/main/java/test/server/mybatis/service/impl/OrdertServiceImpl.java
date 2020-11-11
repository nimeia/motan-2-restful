package test.server.mybatis.service.impl;

import test.server.mybatis.entity.Ordert;
import test.server.mybatis.mapper.OrdertMapper;
import test.server.mybatis.service.IOrdertService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OrdertServiceImpl extends ServiceImpl<OrdertMapper, Ordert> implements IOrdertService {

}
