package test.server.impl;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.api.DemoApi;
import test.api.User;

@RestController
@RequestMapping("_api")
public class DemoApiRestfulImpl implements DemoApi {

    @Override
    @RequestMapping
    public String hello(@RequestParam("world") String world) {
        return "restful hello !" +world;
    }

    @Override
    @RequestMapping("user")
    public User updateUser(@RequestBody User user) {
        user.setRemark("restful 信息已经修改");

        return user;
    }
}
