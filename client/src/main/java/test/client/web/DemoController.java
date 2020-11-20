package test.client.web;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.api.*;

import java.math.BigDecimal;

@RestController
public class DemoController {

    @MotanReferer
    DemoApi demoApi;

    @Autowired
    DemoRestApi demoRestApi;

    @RequestMapping("")
    public Object index() {
        return demoApi.hello("!  world");
    }

    @RequestMapping("user")
    public Object user() {
        User user = new User();
        user.setAge(100);
        user.setMoney(new BigDecimal(100));
        user.setName("huang");
        return demoApi.updateUser(user);
    }

    @RequestMapping("restful")
    public Object restfulIndex() {
        return demoRestApi.hello("!  world");
    }

    @RequestMapping("restful/user")
    public Object restfulUser() {
        User user = new User();
        user.setAge(100);
        user.setMoney(new BigDecimal(100));
        user.setName("huang");

        //多参数封装例子
        RpcRequestVo<User> rpcRequestVo = new RpcRequestVo<>();
        //设置一些分页参数
        rpcRequestVo.setPageNumber(0);
        rpcRequestVo.setPageSize(10);
        //传递一些排序参数
        RpcRequestOrderVo rpcRequestOrderVo = new RpcRequestOrderVo("name", RpcRequestOrderVo.ORDER_TYPE_DESC);
        rpcRequestVo.addOrder(rpcRequestOrderVo);
        rpcRequestVo.setArgs(user);

        demoRestApi.update(rpcRequestVo);


        return demoRestApi.updateUser(user);
    }

}
