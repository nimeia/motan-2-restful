package test.client.web;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.api.DemoApi;
import test.api.DemoRestApi;
import test.api.User;

import java.math.BigDecimal;

@RestController
public class DemoController {

    @MotanReferer
    DemoApi demoApi;

    @Autowired
    DemoRestApi demoRestApi;

    @RequestMapping("")
    public Object index(){
        return demoApi.hello("!  world");
    }

    @RequestMapping("user")
    public Object user(){
        User user = new User();
        user.setAge(100);
        user.setMoney(new BigDecimal(100));
        user.setName("huang");
        return demoApi.updateUser(user);
    }

    @RequestMapping("restful")
    public Object restfulIndex(){
        return demoRestApi.hello("!  world");
    }

    @RequestMapping("restful/user")
    public Object restfulUser(){
        User user = new User();
        user.setAge(100);
        user.setMoney(new BigDecimal(100));
        user.setName("huang");
        return demoRestApi.updateUser(user);
    }

}
