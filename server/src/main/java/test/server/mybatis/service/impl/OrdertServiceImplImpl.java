package test.server.mybatis.service.impl;

import test.server.mybatis.entity.Ordert;
import test.server.mybatis.mapper.OrdertMapper;
import test.server.mybatis.service.IOrdertService;
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
public class OrdertServiceImplImpl extends BaseServiceImpl<OrdertMapper, Ordert> implements IOrdertService{

}
