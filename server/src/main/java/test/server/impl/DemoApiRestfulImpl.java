package test.server.impl;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.api.DemoApi;
import test.api.Order;
import test.api.RpcRequestVo;
import test.api.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("_api")
public class DemoApiRestfulImpl implements DemoApi {

    @Override
    @RequestMapping
    public String hello(@RequestParam("world") String world) {
        return "restful hello !" + world;
    }

    @Override
    @RequestMapping("user")
    public User updateUser(@RequestBody User user) {
        user.setRemark("restful 信息已经修改");

        return user;
    }

    /**
     * 只是用于演示多参数转换
     * 在spring mvc  + feign 的场景无法使用，需要使用一个对象包裹
     *
     * @param user
     * @param order
     */
    @Override
    public void update(@RequestBody User user, @RequestBody Order order) {
        // 只是用于演示多参数转换
    }

    /**
     * 替换例子，把motan 的多参数封装成一个参数传输
     *
     * @param rpcRequestVo
     * @return
     */
    @RequestMapping("update")
    List<User> update(@RequestBody RpcRequestVo<User> rpcRequestVo) {
        List<User> list = new ArrayList<>();
        return list;
    }

}
