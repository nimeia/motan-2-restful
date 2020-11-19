package test.server.impl;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import test.api.DemoApi;
import test.api.User;

@MotanService(export = "demoMotan:8002")
public class DemoApiImpl implements DemoApi {
    @Override
    public String hello(String world) {
        return "hello:" + world;
    }

    @Override
    public User updateUser(User user) {
//        User user1 = new User();
        user.setRemark("信息已经修改");
        return user;
    }
}
