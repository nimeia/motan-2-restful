package test.server.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.api.User;
import test.server.entity.UserEntity;
import test.server.entity.UserPo;
import test.server.service.UserService;

import java.util.List;

@RestController
@RequestMapping("")
public class OrmController {

    @Autowired
    UserService userService;

    @RequestMapping("jpa")
    public Object jpa(){
        List<UserEntity> user = userService.findAll();
        return user;
    }

    @RequestMapping("mybatis")
    public Object mybatis(){
        List<UserPo> userPos = userService.findAllUserPo();

        return userPos;
    }
}
